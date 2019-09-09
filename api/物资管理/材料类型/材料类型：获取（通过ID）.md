# 简介

# 访问地址
storehouse-material-category/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|记录的ID|

## 请求示例
```json
{
    "id": 8
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
        "id": 8,
        "createTime": "2019-09-09",
        "updateTime": "2019-09-09",
        "remarks": null,
        "name": "机电D-2",
        "parentId": 6,
        "path": "1-6-8-",
        "children": null
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。