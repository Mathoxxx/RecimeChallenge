# RecimeChallenge - Mathias Gonzalez
The challenge is about creating a RESTful API for managing recipes, built with 
**Spring Boot 3.5**, 
**JPA/Hibernate with PostgreSQL 14.18**, 
**Lombok**, and
**Maven**

### Features

- CRUD operations for recipes
- Filter parameters for retrieving recipes
- DTO mapping with MapStruct
- Default swagger by springdoc
- Proper HTTP status handling (e.g. `404 Not Found`, `201 Created`)
- JSON request/response

###  Prerequisites

- Java 17+
- Maven
- PostgreSQL running and accessible

###  Configuration

1. **Clone the repo**

git clone https://github.com/Mathoxxx/RecimeChallenge.git

2. **Database setup** -
Connect to PostgreSQL and run:

CREATE DATABASE postgres;

CREATE USER postgres WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

3. **Service run**

Now we can run the application, once it is up we can observe the operations it exposes on http://localhost:8080/swagger-ui/index.html