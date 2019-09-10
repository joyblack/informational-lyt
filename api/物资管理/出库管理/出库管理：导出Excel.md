# 简介
导出当前查询结果的全部数据。

# 访问地址
gm-inventory-out/exportData

> 若使用URL拼接提交参数方式，请使用接口`gm-inventory-in/exportData2`

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数|类型|必填|简介|
|-|-|-|-|
|name|[string]||材料名称|
|materialCategoryId|[number]||材料类别ID|
|storehouseId|[number]||出库仓库的ID|
|modelNumber|[string]||型号|
|supplierId|[number]||供货商ID|
|outDateStart|[date]||出库的时间区间起|
|outDateEnd|[date]||出库的时间区间止|
|usedTeam|[string]||领用班组|


## 请求示例
```json
{
	"name": "牛奶"
}
```

# 返回结果
**成功**
```
Excel文件流
```

**失败**
若传递非法参数，则会出现查询异常。
```json
{
    "code": 406,
    "detailMessage": "\r\n### Error querying...",
    "state": false,
    "data": null,
    "message": "服务器繁忙，请稍后再试"
}
```
