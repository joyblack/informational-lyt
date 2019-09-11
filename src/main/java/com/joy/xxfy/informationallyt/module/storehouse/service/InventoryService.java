package com.joy.xxfy.informationallyt.module.storehouse.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationallyt.module.common.constant.ExcelConstant;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.*;
import com.joy.xxfy.informationallyt.module.storehouse.domain.repository.*;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryGetListReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInGetListReq;
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
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;


    /**
     * 获取数据(id)
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(inventoryRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(InventoryGetListReq req) {
        return JoyResult.buildSuccessResultWithData(inventoryRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(InventoryGetListReq req) {
        return JoyResult.buildSuccessResultWithData(inventoryRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<InventoryEntity> getPredicates(InventoryGetListReq req){
        return (Specification<InventoryEntity>) (root, query, builder) -> {
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
             * 材料类别
             */
            if(req.getMaterialCategoryId() != null){
                predicates.add(builder.equal(root.get("material").get("materialCategory").get("id"), req.getMaterialCategoryId()));
            }
            /**
             * 仓库
             */
            if(req.getStorehouseId() != null){
                predicates.add(builder.equal(root.get("storehouse").get("id"), req.getStorehouseId()));
            }
            query.groupBy(root.get("material"));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    public void exportData(InventoryGetListReq req, HttpServletRequest request, HttpServletResponse response) {
        // 获取查询结果
        List<InventoryInEntity> inventoryIns = (List<InventoryInEntity>)getAllList(req).getData();
        // 日期导出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(ExcelConstant.DATE_FORMAT);
        List<List<Object>> rows = new ArrayList<>();
        rows.add(CollUtil.newArrayList(ExcelConstant.HEAD_INVENTORY_IN));
        for (int i = 0; i < inventoryIns.size(); i++) {
            InventoryInEntity info = inventoryIns.get(i);
            List<Object> row = CollUtil.newArrayList(
                    // 序号（也可以使用ID?）
                    i + 1,
                    // 材料名称
                    info.getMaterial().getName(),
                    // 型号
                    info.getMaterial().getModelNumber(),
                    // 供货单位/供货人
                    info.getSupplier().getName(),
                    // 材料类别
                    info.getMaterial().getMaterialCategory().getName(),
                    // 库存总数（从材料对象处获得）
                    info.getMaterial().getAmount(),
                    // 入库数量
                    info.getInNumber().toString(),
                    // 入库仓库
                    info.getStorehouse().getName(),
                    // 入库后总数
                    info.getAfterAmount(),
                    // 入库时间
                    dateFormat.format(info.getInDate()),
                    // 签收人
                    info.getSignPeople(),
                    // 备注
                    info.getRemarks()
            );
            rows.add(row);
        }
        String fileName = "库存信息表";
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(rows, true);
        ExportUtil.exportData(request, response, fileName, writer);
    }
}
