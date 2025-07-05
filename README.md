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

###  Configuration setup

1. **Clone the repo**

git clone https://github.com/Mathoxxx/RecimeChallenge.git

2. **Database setup** -
Connect to PostgreSQL and run:

CREATE DATABASE postgres;

CREATE USER postgres WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

3. **Service run**

Now we can run the application, once it is up we can observe the operations it exposes on http://localhost:8080/swagger-ui/index.html

###  Design choices and assumptions
_**Layered Architecture:**_
The application follows a layered architecture to enforce separation of responsibilities:

-Controller Layer: Handles HTTP requests/responses and maps endpoints.

-Service Layer: Contains business logic and validations.

-Repository Layer: Responsible for data persistence using Spring Data JPA.

-DTO & Mapper Layer: Isolates internal domain models from external representations.

_**DTOs + MapStruct:**_
Instead of exposing JPA entities directly, I use Data Transfer Objects (DTOs) and MapStruct to:

-Avoid tight coupling between the database and API.

-Ensure clean API contracts.

-Improve performance by avoiding lazy-loading issues in JSON serialization.

MapStruct was chosen for its compile-time performance and clean syntax.

_**Swagger / OpenAPI Documentation:**_
The project integrates Springdoc OpenAPI to provide a live, interactive API documentation UI using Swagger.

_**Entities Design:**_

-The Recipe entity models the core concept of a cooking recipe. It was designed with clarity, usability, and future extensibility in mind.
Represent real-world recipe data (name, instructions, ingredients)
Ensure a normalized, relational structure (e.g. link to Ingredient entity)
Allow future expansion (e.g. tags, ratings, difficulty, cook time).

-The Ingredient entity represents a unique item that can be used in multiple recipes. It was designed to support a normalized schema and allow reuse across the database.
This approach prevents duplication (e.g., “Sugar” appearing multiple times) and enables features like ingredient management or tagging in the future.

Relationship with Recipes
The Ingredient entity is connected to Recipe indirectly via the join entity RecipeIngredient. This allows:
One ingredient to be reused in multiple recipes
Tracking amount and units per recipe use

Keeps ingredients normalized

Avoids name duplication in the database

Enables future features like filtering recipes by ingredient or nutrition data


Recipe (1) ────< RecipeIngredient >──── (1) Ingredient

Reusability: Ingredients are shared across recipes without duplication

Normalization: Clean relational model that avoids redundant data

Extendable: You can add fields later (e.g., calories, allergens, categories) without affecting core logic