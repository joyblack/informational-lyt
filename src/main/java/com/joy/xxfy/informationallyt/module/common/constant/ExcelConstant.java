package com.joy.xxfy.informationallyt.module.common.constant;

public class ExcelConstant {
    // 日期导出格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // 入库表头
    public static final String[] HEAD_INVENTORY_IN = {"序号", "材料名称", "型号", "供货单位/供货人",
            "材料类别", "库存总数", "入库数量","入库仓库", "入库后总数", "入库时间","签收人","备注"};

    // 出库表头
    public static final String[] HEAD_INVENTORY_OUT = {"序号", "材料名称", "型号",
            "材料类别", "库存总数", "出库数量","出库仓库", "出库后总数", "出库时间","领用班组","备注"};

    // 隐患整改表头
    public static final String[] HEAD_INSPECTION = {"序号", "检查日期", "检查单位", "隐患内容",
            "整改措施", "整改负责人", "整改期限（天）","投入资金（元）", "验收日期", "验收结果","验收人","备注"};
}
