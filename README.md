# Simple API for User CRUD

 
1. Open in IDE

2. Configure database connection in **src/main/resources/application.properties**

```
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD
spring.datasource.url=jdbc:mysql://localhost:3306/DATABASE_NAME
spring.jpa.hibernate.ddl-auto=update // Update database without destroy old schema
```

3. Run


4. Services

```
GET /users 
Functions: Get all users


GET /users/userNameorPassword
Function: Get user by username or password


POST /users
Body:
{
 username: String!
 email: String!
 address: String!
}
Function: Get user by username or password


PUT /users/userId
Body:
{
 username: String
 email: String
 address: String
}
Function: Update user by userId


DELETE /users/userId
Function: Delete user by userID
```
