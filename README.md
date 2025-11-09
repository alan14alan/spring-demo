# spring-demo
初始化SQL在resource內 data.sql及scheme.sql

API

幣別資料表 CRUD API
GET http://localhost:8080/api/v1/currency  getAll 

GET http://localhost:8080/api/v1/currency/{code} get by code 

POST http://localhost:8080/api/v1/currency 新增

{
 "code":"JPY",
 "chineseName":"日圓"
}

PUT http://localhost:8080/api/v1/currency/{code} update by code

{
 "chineseName":"新美元"
}

轉換API

GET http://localhost:8080/api/v1/coindesk/original

GET http://localhost:8080/api/v1/coindesk/transformed