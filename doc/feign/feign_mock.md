# Feign Mock

````
格式：GET:http://user-service/api/user/{userId}: remote|http://xxx.com/mock/api/1001
格式：GET:http://user-service/api/user/{userId}: local|100.json
格式：GET:http://user-service/api/user/{userId}: db_json|http://xxx.com/mock/api/10001
格式：GET:http://user-service/api/user/{userId}:db_remote|http://xxx.com/mock/api/10001
````