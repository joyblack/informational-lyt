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



    STAFF_ALREADY_IN_DEPARTMENT("员工已经存在于此部门下", 103001),

    // store
    STOREHOUSE_NOT_EXIST("仓库信息不存在", 105000),
    STOREHOUSE_NAME_ALREADY_EXIST("仓库名称已存在", 105001),


    // 安全
    SAFE_INSPECTION_NOT_EXIST("安全巡检信息不存在", 112000),
    SAFE_INSPECTION_ALREADY_EXIST("安全巡检信息已存在", 112001),
    VIOLATION_NOT_EXIST("违章信息不存在", 112002),

    // 设备
    DEVICE_CATEGORY_PARENT_NOT_EXIST("父设备类型信息不存在", 113000),
    DEVICE_CATEGORY_NOT_EXIST("设备类型信息不存在", 113001),
    DEVICE_CATEGORY_ALREADY_EXIST("设备类型信息已存在", 113002),
    DEVICE_CATEGORY_NAME_EXIST("设备类型名称已存在", 113003),
    DEVICE_INFO_ALREADY_EXIST("设备信息已存在", 113004),
    DEVICE_INFO_NOT_EXIST("设备信息不存在", 113005),
    DEVICE_INFO_NAME_NOT_EXIST("设备名称不存在", 113006),

    DEVICE_MAINTAIN_ALREADY_EXIST("设备维保信息已存在", 113007),
    DEVICE_MAINTAIN_NOT_EXIST("设备维保信息不存在", 113008),

    // file
    UPLOAD_FILE_IS_NULL("上传文件为空", 108000),
    UPLOAD_FILE_ERROR("上传文件错误", 108001),
    UPLOAD_FILE_FAILED("上传文件失败", 108002),
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
