package com.joy.xxfy.informationallyt.module.storehouse.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationallyt.module.common.constant.ExcelConstant;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.*;
import com.joy.xxfy.informationallyt.module.storehouse.domain.repository.*;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInGetListReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryOutAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryOutGetListReq;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.exception.JoyException;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationallyt.publish.utils.JpaPagerUtil;
import com.joy.xxfy.informationallyt.publish.utils.StringUtil;
import com.joy.xxfy.informationallyt.publish.utils.excel.ExportUtil;
import com.joy.xxfy.informationallyt.validated.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryOutService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryOutRepository inventoryOutRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialCategoryRepository materialCategoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StorehouseRepository storehouseRepository;

    /**
     * 出库
     */
    public JoyResult add(InventoryOutAddReq req){
        /**
         * 获取本次出库所需的基本数据信息：类别、供货商、仓库
         */
        // 类别信息
        MaterialCategoryEntity materialCategory = materialCategoryRepository.findAllById(req.getMaterialCategoryId());
        if(materialCategory == null){
            return JoyResult.buildFailedResult(Notice.MATERIAL_CATEGORY_NOT_EXIST);
        }
        // 供货商信息
        SupplierEntity supplier = supplierRepository.findAllById(req.getSupplierId());
        if(supplier == null){
            return JoyResult.buildFailedResult(Notice.SUPPLIER_NOT_EXIST);
        }
        // 仓库
        StorehouseEntity storehouse = storehouseRepository.findAllById(req.getStorehouseId());
        if(storehouse == null){
            return JoyResult.buildFailedResult(Notice.STOREHOUSE_NOT_EXIST);
        }
        // 是否存在材料信息
        MaterialEntity material = materialRepository.findFirstByNameAndModelNumberAndSupplier(req.getName(), req.getModelNumber(), supplier);
        if(material == null){
            return JoyResult.buildFailedResult(Notice.MATERIAL_NOT_EXIST);
        }
        // 检测库存是否足够
        InventoryEntity inventory = inventoryRepository.findFirstByStorehouseAndMaterial(storehouse, material);
        if(inventory == null){
            return JoyResult.buildFailedResult(Notice.INVENTORY_NOT_EXIST);
        }
        if(inventory.getAmount() < req.getOutNumber()){
            return JoyResult.buildFailedResult(Notice.INVENTORY_AMOUNT_NOT_ENOUGH,"仓库【"+ storehouse.getName()  +"】中材料【" + material.getName() + "】库存不足，剩余: " + inventory.getAmount() + "，小于出库量: " + req.getOutNumber());
        }

        // 修改材料：总数信息
        material.setAmount(material.getAmount() - req.getOutNumber());
        material = materialRepository.save(material);

        // 修改库存: 库存信息
        inventory.setAmount(inventory.getAmount() - req.getOutNumber());
        inventoryRepository.save(inventory);

        // 记录出库日志
        InventoryOutEntity log = new InventoryOutEntity();
        // 一些可直接copy的信息
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, log);
        // 材料
        log.setMaterial(material);
        // 仓库
        log.setStorehouse(storehouse);
        return JoyResult.buildSuccessResultWithData(inventoryOutRepository.save(log));
    }

    /**
     * 批量出库
     */
    public JoyResult batchAdd(ValidList<InventoryOutAddReq> reqs){
        for (InventoryOutAddReq req : reqs) {
            // 类别信息
            MaterialCategoryEntity materialCategory = materialCategoryRepository.findAllById(req.getMaterialCategoryId());
            if(materialCategory == null){
                throw new JoyException(Notice.MATERIAL_CATEGORY_NOT_EXIST);
            }
            // 供货商信息
            SupplierEntity supplier = supplierRepository.findAllById(req.getSupplierId());
            if(supplier == null){
                throw new JoyException(Notice.SUPPLIER_NOT_EXIST);
            }
            // 仓库
            StorehouseEntity storehouse = storehouseRepository.findAllById(req.getStorehouseId());
            if(storehouse == null){
                throw new JoyException(Notice.STOREHOUSE_NOT_EXIST);
            }
            // 是否存在材料信息
            MaterialEntity material = materialRepository.findFirstByNameAndModelNumberAndSupplier(req.getName(), req.getModelNumber(), supplier);
            if(material == null){
                throw new JoyException(Notice.MATERIAL_NOT_EXIST);
            }
            // 检测库存是否足够
            InventoryEntity inventory = inventoryRepository.findFirstByStorehouseAndMaterial(storehouse, material);
            if(inventory == null){
                throw new JoyException(Notice.INVENTORY_NOT_EXIST);
            }
            if(inventory.getAmount() < req.getOutNumber()){
                throw new JoyException("仓库【"+ storehouse.getName()  +"】中材料【" + material.getName() + "】库存不足，剩余: " + inventory.getAmount() + "，小于出库量: " + req.getOutNumber());
            }

            // 修改材料：总数信息
            material.setAmount(material.getAmount() - req.getOutNumber());
            material = materialRepository.save(material);

            // 修改库存: 库存信息
            inventory.setAmount(inventory.getAmount() - req.getOutNumber());
            inventoryRepository.save(inventory);

            // 记录出库日志
            InventoryOutEntity log = new InventoryOutEntity();
            // 一些可直接copy的信息
            JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, log);
            // 材料
            log.setMaterial(material);
            // 仓库
            log.setStorehouse(storehouse);
            inventoryOutRepository.save(log);
        }
        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_ADD_SUCCESS);
    }


    /**
     * 获取数据(id)
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(inventoryOutRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(InventoryOutGetListReq req) {
        return JoyResult.buildSuccessResultWithData(inventoryOutRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(InventoryOutGetListReq req) {
        return JoyResult.buildSuccessResultWithData(inventoryOutRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<InventoryOutEntity> getPredicates(InventoryOutGetListReq req){
        return (Specification<InventoryOutEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            /**
             * 材料名称
             */
            if(!StringUtil.isEmpty(req.getName())){
                predicates.add(builder.like(root.get("material").get("name"), "%" + req.getName() +"%"));
            }
            /**
             * 型号
             */
            if(!StringUtil.isEmpty(req.getModelNumber())){
                predicates.add(builder.like(root.get("material").get("modelNumber"), "%" + req.getModelNumber() +"%"));
            }
            /**
             * 领用班组
             */
            if(!StringUtil.isEmpty(req.getUsedTeam())){
                predicates.add(builder.like(root.get("usedTeam"), "%" + req.getUsedTeam() +"%"));
            }
            /**
             * 材料类别
             */
            if(req.getMaterialCategoryId() != null){
                predicates.add(builder.equal(root.get("material").get("materialCategory").get("id"), req.getMaterialCategoryId()));
            }
            /**
             * 仓库名称
             */
            if(req.getStorehouseId() != null){
                predicates.add(builder.equal(root.get("storehouse").get("id"), req.getStorehouseId()));
            }

            /**
             * 供应商
             */
            if(req.getSupplierId() != null){
                predicates.add(builder.equal(root.get("material").get("supplier").get("id"), req.getSupplierId()));
            }
            /**
             * 出库时间区间
             */
            if(req.getOutDateStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("outDate"), req.getOutDateStart()));
            }
            if(req.getOutDateEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("outDate"), req.getOutDateEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    public void exportData(InventoryOutGetListReq req, HttpServletRequest request, HttpServletResponse response) {
        // 获取查询结果
        List<InventoryOutEntity> inventoryIns = (List<InventoryOutEntity>)getAllList(req).getData();
        // 日期导出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(ExcelConstant.DATE_FORMAT);
        List<List<String>> rows = new ArrayList<List<String>>();
        rows.add(CollUtil.newArrayList(ExcelConstant.HEAD_INVENTORY_OUT));
        for (int i = 0; i < inventoryIns.size(); i++) {
            InventoryOutEntity info = inventoryIns.get(i);
            List<String> row = CollUtil.newArrayList(
                    // 序号（也可以使用ID?）
                    String.valueOf(i + 1),
                    // 材料名称
                    info.getMaterial().getName(),
                    // 型号
                    info.getMaterial().getModelNumber(),
                    // 供货单位/供货人
                    info.getMaterial().getSupplier().getName(),
                    // 材料类别
                    info.getMaterial().getMaterialCategory().getName(),
                    // 库存总数（从材料对象处获得）
                    info.getMaterial().getAmount().toString(),
                    // 出库数量
                    info.getOutNumber().toString(),
                    // 出库仓库
                    info.getStorehouse().getName(),
                    // 出库后总数
                    info.getAfterAmount().toString(),
                    // 出库时间
                    dateFormat.format(info.getOutDate()),
                    // 领用班组
                    info.getUsedTeam(),
                    // 备注
                    info.getRemarks()
            );
            rows.add(row);
        }
        String fileName = "出库信息表";
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(rows, true);
        ExportUtil.exportData(request, response, fileName, writer);
    }
}
