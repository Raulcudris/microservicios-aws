# Spring Boot Microservices Project
(Eureka Server, Config Server, API Gateway, Kafka, File Storage, JWT, Authentication, Authorization, Redis, Docker)

# About the project

<ul style="list-style-type:disc">
  <li>This project is based Spring Boot Microservices</li>
  <li>User can register and login through auth service by user role (ADMIN or USER) through api gateway</li>
  <li>User can send any request to relevant service through api gateway with its bearer token</li>
</ul>

8 services whose name are shown below have been devised within the scope of this project.

- Config Server
- Eureka Server
- API Gateway
- Auth Service
- User Service



### Used Dependencies

* Core
    * Spring
        * Spring Boot
        * Spring Security
            * Spring Security JWT
            * Authentication
            * Authorization
        * Spring Web
            * FeighClient
        * Spring Data
            * Spring Data JPA
            * PostgreSQL
        * Spring Cloud
            * Spring Cloud Gateway Server
            * Spring Cloud Config Server
            * Spring Cloud Config Client
    * Netflix
        * Eureka Server
        * Eureka Client
* Database
    * PostgreSQL



### Explore Rest APIs

<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th>
      <th>Valid Request Body</th>
      <th>Valid Request Params</th>
  </tr>
  <tr>
      <td>POST</td>
      <td>/v1/auth/register</td>
      <td>Register for User</td>
      <td><a href="#register">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>/v1/auth/login</td>
      <td>Login for User and Admin</td>
      <td><a href="#login">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/user/getAll</td>
      <td>Get all user</td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/user/getUserById/{id}</td>
      <td>Get user by id</td>
      <td></td>
      <td><a href="#getUserById">Info</a></td>
  </tr>
 <tr>
      <td>GET</td>
      <td>/v1/user/getUserByEmail/{email}</td>
      <td>Get user by email</td>
      <td></td>
      <td><a href="#getUserByEmail">Info</a></td>
  </tr>
 <tr>
      <td>PUT</td>
      <td>/v1/user/update</td>
      <td>Update user</td>
      <td><a href="#updateUser">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>DELETE</td>
      <td>/v1/user/deleteUserById/{id}</td>
      <td>Delete user by id</td>
      <td></td>
      <td><a href="#deleteUserById">Info</a></td>
  </tr>

## Valid Request Body

##### <a id="register"> Register for User

``` 
    http://localhost:8080/v1/auth/register
    
        {
          "username": "string",
          "password": "string",
          "email": "string"
        }
```

##### <a id="login"> Login for User and Admin

```
      http://localhost:8080/v1/auth/login
    
       {
         "username": "string",
         "password": "string"
       }
```

##### <a id="updateUser"> Update User

```
    http://localhost:8080/v1/user/update
    
    form-data:
        {
          "request": {
            "id": "string",
            "username": "string",
            "password": "string",
            "userDetails": {
              "firstName": "string",
              "lastName": "string",
              "phoneNumber": "string",
              "country": "string",
              "city": "string",
              "address": "string",
              "postalCode": "string",
              "aboutMe": "string",
              "profilePicture": "string"
            }
           },
         "file": "string"
        }
    
    Bearer Token : Authorized User or Admin
```
