# 简介
导出当前查询结果的全部数据。

# 访问地址
safe-inspection/exportData

> 若使用URL拼接提交参数方式，请使用接口`safe-inspection/exportData2`

# 请求参数

## 请求方式
POST/GET

## 请求格式
JSON/URL PARAMETER

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|checkDateStart|[date]||检查日期开始时间|
|checkDateEnd|[date]||检查日期截止时间|
|checkDepartmentId|[number]||检查单位ID，其实就是部门信息ID|
|examineDateStart|[date]||验收日期的开始时间|
|examineDateEnd|[date]||验收截止日期的截止时间|
|examineResult|[string]||验收结果|
|rectificationResponsePeople|[string]||整改负责人|
|problemContent|[string]||隐患内容|

## 请求示例
```json
{
	"name": "棒棒糖",
	"storehouseId": 2
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
