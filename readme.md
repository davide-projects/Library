# 📚 Library REST API

A RESTful API built with Spring Boot for managing a library system. Supports full CRUD operations for books, members, and loans, with 🔐 Basic authentication, input validation, and custom exception handling.
 
---

## 🛠️ Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.3.4 |
| Spring Security | Basic Auth |
| Spring Data JPA | - |
| MySQL | 8.0 |
| Swagger (SpringDoc OpenAPI) | 2.6.0 |
| Maven | - |
 
---

## 📁 Project Structure

```
src/main/java/com/apulia/library/
├── controller/       # REST Controllers
├── service/          # Business Logic
├── repository/       # JPA Repositories
├── model/            # JPA Entities
├── dto/              # Data Transfer Objects
└── exception/        # Custom Exceptions & GlobalExceptionHandler
```
 
---

## 🗄️ Database

- **Database:** MySQL
- **Schema:** `library_db`
- **Port:** `3307`
### Entities

**Book**
| Field | Type | Constraints |
|---|---|---|
| id | Integer | PK, Auto Increment |
| title | String | Not Blank, max 255 |
| author | String | Not Blank, max 255 |
| publisher | String | Not Blank, max 255 |

**Member**
| Field | Type | Constraints |
|---|---|---|
| id | Integer | PK, Auto Increment |
| firstName | String | Not Blank, max 100 |
| lastName | String | Not Blank, max 100 |
| city | String | Not Blank, max 100 |
| phone | String | Not Blank, unique, Italian format |

**Loan**
| Field | Type | Constraints |
|---|---|---|
| id | Integer | PK, Auto Increment |
| book | Book | Not Null, FK |
| member | Member | Not Null, FK |
| loanDate | LocalDate | Not Null, auto-set |
| returnDate | LocalDate | Nullable |
 
---

## 🚀 Endpoints

### 📖 Book `/book`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/book` | Get all books |
| GET | `/book/{id}` | Get book by ID |
| POST | `/book` | Create a new book |
| PUT | `/book/{id}` | Update a book |
| PATCH | `/book/{id}` | Partial update a book |
| DELETE | `/book/{id}` | Delete a book |
| GET | `/book/search?author=&title=` | Search books by author and/or title |

### 👤 Member `/member`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/member` | Get all members |
| GET | `/member/{id}` | Get member by ID |
| POST | `/member` | Create a new member |
| PUT | `/member/{id}` | Update a member |
| PATCH | `/member/{id}` | Partial update a member |
| DELETE | `/member/{id}` | Delete a member |
| GET | `/member/search` | Search members by name, city or phone |

### 📋 Loan `/loan`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/loan` | Get all loans |
| GET | `/loan/{id}` | Get loan by ID |
| GET | `/loan/member/{memberId}` | Get loans by member |
| GET | `/loan/book/{bookId}` | Get loans by book |
| GET | `/loan/member/{memberId}/active` | Get active loans by member |
| POST | `/loan` | Create a new loan |
| PATCH | `/loan/{id}/return` | Return a book |
| DELETE | `/loan/{id}` | Delete a loan |
 
---

## ▶️ Getting Started

### Prerequisites
- Java 21
- MySQL 8.0
- Maven
### Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/library_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Run the project

```bash
mvn spring-boot:run
```
 
---

## 📄 API Documentation

Swagger UI available at:

```
http://localhost:8080/swagger-ui/index.html
```
 
---

## 🔐 Authentication

The API uses **Basic Authentication**. Include your credentials in every request:

- In Postman: Authorization → Basic Auth
- In Swagger: click the 🔒 Authorize button
---

## ⚠️ Error Handling

All errors return a consistent `ErrorResponse` object:

```json
{
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Book with id 99 not found"
}
```

| Status | Description |
|---|---|
| 400 | Bad Request / Validation Error |
| 401 | Unauthorized |
| 404 | Resource Not Found |
| 500 | Internal Server Error |