package com.joy.xxfy.informationallyt.module.storehouse.web;

import com.joy.xxfy.informationallyt.module.common.web.req.IdReq;
import com.joy.xxfy.informationallyt.module.storehouse.service.InventoryInService;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInGetListReq;
import com.joy.xxfy.informationallyt.publish.exception.JoyException;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.validated.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("gm-inventory-in")
public class InventoryInController {

    @Autowired
    private InventoryInService inventoryInService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid InventoryInAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryInService.add(req);
        }
    }

    /**
     * 添加（批量）
     */
    @PostMapping(
            value = "/batchAdd",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid  ValidList<InventoryInAddReq> reqs, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryInService.batchAdd(reqs);
        }
    }


    /**
     * 获取
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryInService.get(idRequest.getId());
        }
    }



    /**
     * 获取全部列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid InventoryInGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryInService.getAllList(req);
        }
    }

    /**
     * 分页
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid InventoryInGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryInService.getPagerList(req);
        }
    }

    /**
     * 导出查询结果的数据
     */
    @RequestMapping("exportData")
    public void exportData(@RequestBody @Valid InventoryInGetListReq req, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            throw new JoyException(Notice.REQUEST_PARAMETER_IS_ERROR);
        } else {
            inventoryInService.exportData(req, request, response);
        }
    }

    @RequestMapping("exportData2")
    public void exportData2(@Valid InventoryInGetListReq req, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            throw new JoyException(Notice.REQUEST_PARAMETER_IS_ERROR);
        } else {
            inventoryInService.exportData(req, request, response);
        }
    }


}
