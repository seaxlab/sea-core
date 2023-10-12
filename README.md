# sea-core

## 使用说明

- [Usage](doc/00usage.md)

## 注意事项

- 如果用了`JSON.isValid()`,则必须升级到1.2.25及以上
- 统一使用Fastjson，禁止直接使用json库API,便于统一管理和升级。fastjson有一些潜在的漏洞问题
- Result只适用用在外部接口之间的交互