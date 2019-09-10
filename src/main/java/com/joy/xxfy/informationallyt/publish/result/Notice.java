package com.joy.xxfy.informationallyt.publish.result;

public enum Notice{
    EXECUTE_IS_SUCCESS("操作成功", 200),
    EXECUTE_IS_FAILED("操作失败", 234),

    PERMISSION_FORBIDDEN("权限禁止", 235),
    IMPORT_FILE_IS_NULL("导入文件为空", 240),
    NAME_ALREADY_EXIST("名称已存在", 241),
    DATA_NOT_EXIST("数据信息不存在", 242),
    PARENT_DATA_NOT_EXIST("父节点数据信息不存在", 243),
    DATA_IN_USED_CANT_BE_DELETE("该数据正在使用中，不允许删除。", 299),

    // 公共模块
    REQUEST_PARAMETER_IS_ERROR("请求参数缺失或为空值", 100000),
    IDENTITY_NUMBER_ERROR("身份证信息错误", 100001),
    PHONE_ERROR("电话号码错误", 100002),
    PASSWORD_AFFIRM_ERROR("确认密码不匹配", 100003),
    IDENTITY_NUMBER_ALREADY_EXIST("身份证号码已存在", 100005),
    PHONE_ALREADY_EXIST("电话号码已存在", 100006),
    LOGIN_NAME_ALREADY_EXIST("登录名已存在", 100007),
    PASSWORD_ERROR("密码错误", 100008),
    LOGIN_NAME_PASSWORD_NOT_MATCH("登录名与密码不匹配", 100009),



    // user
    USER_NOT_LOGIN("用户未登录", 101000),
    USER_NOT_EXIST("用户信息不存在", 101001),
    ROLE_NAME_ALREADY_EXIST("角色名称已存在", 102002),
    ROLE_NOT_EXIST("角色信息不存在", 102003),

    // department
    DEPARTMENT_NOT_EXIST("部门信息不存在",102000),
    DEPARTMENT_PARENT_NOT_EXIST("父部门信息不存在", 102001),
    DEPARTMENT_NAME_ALREADY_EXIST("部门名称已存在", 102002),
    COMPANY_NOT_EXIST("平台/公司信息不存在",102003),

    // 库存
    MATERIAL_CATEGORY_NOT_EXIST("材料类别信息不存在",10300),
    SUPPLIER_NOT_EXIST("供货商信息不存在",10301),
    STOREHOUSE_NOT_EXIST("仓库信息不存在",10300),



    UPLOAD_FILE_ERROR("上传文件错误", 108001),
    UPLOAD_FILE_TYPE_ERROR("文件类型错误", 108003),



    ;


    private String message;
    private int code;

    Notice(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
