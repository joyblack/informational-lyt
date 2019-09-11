# 简介

# 访问地址
gm-inventory-in/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数|类型|必填|简介|
|-|-|-|-|
|id|[number]|是|记录的ID|


## 请求示例
```json
{
	"id": 1
}
```

# 返回结果
**成功**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": {
        "id": 1,
        "createTime": "2019-09-11",
        "updateTime": "2019-09-11",
        "remarks": null,
        "supplier": {
            "id": 5,
            "createTime": "2019-09-11",
            "updateTime": "2019-09-11",
            "remarks": "aiyo,zhangfei!!",
            "name": "电子厂",
            "code": "001-B",
            "business": "出售有机塑料",
            "contactPeople": "张飞",
            "contactPhone": null
        },
        "material": {
            "id": 1,
            "createTime": "2019-09-11",
            "updateTime": "2019-09-11",
            "remarks": null,
            "name": "啤酒",
            "modelNumber": "001-A",
            "materialCategory": {
                "id": 2,
                "createTime": "2019-09-11",
                "updateTime": "2019-09-11",
                "remarks": null,
                "name": "航空航天材料",
                "parentId": 0,
                "path": "2-",
                "children": null
            },
            "amount": 1500,
            "warningAmount": null
        },
        "storehouse": {
            "id": 1,
            "createTime": "2019-09-11",
            "updateTime": "2019-09-11",
            "remarks": null,
            "name": "一号仓库",
            "status": "START",
            "responsePeople": "赵义",
            "code": "001-A",
            "admin": "Mr.Joy",
            "isDefault": "YES"
        },
        "amount": 0,
        "inNumber": 233,
        "afterAmount": 233,
        "inDate": "2019-09-08",
        "signPeople": "赵义"
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。