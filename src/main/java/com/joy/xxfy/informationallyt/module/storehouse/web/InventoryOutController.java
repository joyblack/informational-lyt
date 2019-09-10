package com.joy.xxfy.informationallyt.module.storehouse.web;

import com.joy.xxfy.informationallyt.module.common.web.req.IdReq;
import com.joy.xxfy.informationallyt.module.storehouse.service.InventoryInService;
import com.joy.xxfy.informationallyt.module.storehouse.service.InventoryOutService;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInGetListReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryOutAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryOutGetListReq;
import com.joy.xxfy.informationallyt.publish.exception.JoyException;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.validated.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("gm-inventory-out")
public class InventoryOutController {

    @Autowired
    private InventoryOutService inventoryOutService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid InventoryOutAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryOutService.add(req);
        }
    }

    /**
     * 添加（批量）
     */
    @PostMapping(
            value = "/batchAdd",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid  ValidList<InventoryOutAddReq> reqs, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryOutService.batchAdd(reqs);
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
            return inventoryOutService.get(idRequest.getId());
        }
    }



    /**
     * 获取全部列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid InventoryOutGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryOutService.getAllList(req);
        }
    }

    /**
     * 分页
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid InventoryOutGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return inventoryOutService.getPagerList(req);
        }
    }

    /**
     * 导出查询结果的数据
     */
    @RequestMapping("exportData")
    public void exportData(@RequestBody @Valid InventoryOutGetListReq req, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            throw new JoyException(Notice.REQUEST_PARAMETER_IS_ERROR);
        } else {
            inventoryOutService.exportData(req, request, response);
        }
    }

    @RequestMapping("exportData2")
    public void exportData2(@Valid InventoryOutGetListReq req, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            throw new JoyException(Notice.REQUEST_PARAMETER_IS_ERROR);
        } else {
            inventoryOutService.exportData(req, request, response);
        }
    }


}
