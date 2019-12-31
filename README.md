# Simple API for User CRUD
 
1. Open in IDE

2. Configure database connection in **src/main/resources/application.properties**

```
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD
spring.datasource.url=jdbc:YOUR_RDBMS://localhost:3306/DATABASE_NAME
spring.jpa.hibernate.ddl-auto=update // Update database without destroy old schema
```

3. Run


4. Requests

```
All request must have this header:
**Authentication: simple_api_key_for_authentication**

GET /users 
Operation: Get all users


GET /users/{userNameorPassword}
Operation: Get user by username or password


POST /users
Body:
{
 username: String!
 email: String!
 address: String!
}
Operation: Get user by username or password


PUT /users/{userId}
Body:
{
 email: String
 address: String
}
Operation: Update user by userId


DELETE /users/{userId}
Operation: Delete user by userID
```


