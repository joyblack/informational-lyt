# 简介

# 访问地址
gm-inventory-out/get

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
	"id":1
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
        "createTime": "2019-09-10",
        "updateTime": "2019-09-10",
        "remarks": null,
        "material": {
            "id": 7,
            "createTime": "2019-09-10",
            "updateTime": "2019-09-10",
            "remarks": null,
            "name": "棒棒糖A",
            "materialCategory": {
                "id": 1,
                "createTime": "2019-09-10",
                "updateTime": "2019-09-10",
                "remarks": null,
                "name": "机电A",
                "parentId": 0,
                "path": "1-",
                "children": null
            },
            "supplier": {
                "id": 1,
                "createTime": "2019-09-10",
                "updateTime": "2019-09-10",
                "remarks": "aiyo,zhangfei!!",
                "name": "供货商A",
                "code": "001-A",
                "business": "出售有机塑料",
                "contactPeople": "张飞",
                "contactPhone": null
            },
            "modelNumber": "001-A",
            "amount": 0
        },
        "storehouse": {
            "id": 1,
            "createTime": "2019-09-10",
            "updateTime": "2019-09-10",
            "remarks": null,
            "name": "仓库A",
            "status": "START",
            "responsePeople": "赵义",
            "code": "001-A",
            "admin": "Mr.Joy",
            "isDefault": "YES"
        },
        "amount": 0,
        "outNumber": 233,
        "afterAmount": 100,
        "outDate": "2019-09-08",
        "usedTeam": "三年一班"
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。