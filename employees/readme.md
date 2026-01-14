## Employees Microservice
A microservice for managing employees, I used this to practice implementing various Spring Boot features

### Services
**CRUD Functions:** Classic CRUD functions to query and update an H2 Database.

**JPA Service:** CRUD service implementation leveraging JpaRepository interface for interacting with persistence layer

**JDBC Service:** CRUD service implementation using NamedParameterJdbcTemplate for finer control over database interactions through custom queries.

### Controllers
**EmployeeControllerV1:** A classic controller

**EmployeeControllerV2:** A controller which implementing HATEOAS to return navigation URLs as well as content

**EmployeeControllerAdvice:** A controllerAdvice class handling RuntimeExceptions. Displays Error message as well as timestamp.

### Testing
JUnit tests Mockito mocking for checking functionality of both controllers as well as controllerAdvice.