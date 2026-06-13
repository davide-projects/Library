# 📚 Library REST API + Static Frontend

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![License](https://img.shields.io/badge/License-MIT-yellow)

A complete **Library Management System** built with Spring Boot, featuring:

- REST API for **Books**, **Members**, and **Loans**
- Integrated **static frontend** (HTML + jQuery + Bootstrap)
- **Form Login authentication** (Spring Security + BCrypt)
- **Role-based access** (USER, ADMIN)
- Custom exception handling
- Swagger documentation
---

## 🛠️ Tech Stack

| Technology                  | Version         |
| --------------------------- | --------------- |
| Java                        | 21              |
| Spring Boot                 | 3.3.4           |
| Spring Security             | Form Login      |
| Spring Data JPA             | -               |
| MySQL                       | 8.0             |
| Swagger (SpringDoc OpenAPI) | 2.6.0           |
| Maven                       | -               |
| Frontend                    | HTML, JS, jQuery, Bootstrap |
 
---

## 🖥️ Integrated Frontend

The project includes a static frontend served directly from Spring Boot:

```
src/main/resources/static/
├── components/
│   ├── navbar.html
│   └── modal.html
├── js/
│   ├── books.js
│   ├── members.js
│   ├── loans.js
│   └── modal.js
├── index.html
├── books.html
├── members.html
└── loans.html
```

Access the frontend at `http://localhost:8080` after logging in.
 
---

## 🔐 Authentication

The application uses **Form Login Authentication** (not Basic Auth).

- Custom login page: `/login.html`
- Session-based authentication via `JSESSIONID`
- Passwords hashed with **BCrypt**
- Custom `UserDetailsService`
- Custom `AuthenticationEntryPoint`
### Roles

| Role  | Permissions                        |
| ----- | ---------------------------------- |
| USER  | View books, members, loans         |
| ADMIN | Full CRUD on all resources         |

### Default Credentials (development only)

```
Username: admin
Password: admin
```

> ⚠️ Change these before deploying to any non-local environment.
 
---

## 📁 Project Structure

```
src/main/java/com/apulia/library/
├── controller/       # REST Controllers
├── service/          # Business Logic
├── repository/       # JPA Repositories
├── model/            # JPA Entities
├── dto/              # Data Transfer Objects
├── security/         # SecurityConfig, UserDetailsService, EntryPoint
└── exception/        # Custom Exceptions & GlobalExceptionHandler
 
src/main/resources/static/
├── components/       # Navbar + Modal
├── js/               # Frontend logic
└── *.html            # Pages
```
 
---

## 🗄️ Database

- **Database:** MySQL
- **Schema:** `library_db`
- **Port:** `3307`
### Entities

**Book**

| Field     | Type    | Constraints        |
| --------- | ------- | ------------------ |
| id        | Integer | PK, Auto Increment |
| title     | String  | Not Blank, max 255 |
| author    | String  | Not Blank, max 255 |
| publisher | String  | Not Blank, max 255 |

**Member**

| Field     | Type    | Constraints                        |
| --------- | ------- | ---------------------------------- |
| id        | Integer | PK, Auto Increment                 |
| firstName | String  | Not Blank, max 100                 |
| lastName  | String  | Not Blank, max 100                 |
| city      | String  | Not Blank, max 100                 |
| phone     | String  | Not Blank, unique, Italian format  |

**Loan**

| Field      | Type      | Constraints        |
| ---------- | --------- | ------------------ |
| id         | Integer   | PK, Auto Increment |
| book       | Book      | Not Null, FK       |
| member     | Member    | Not Null, FK       |
| loanDate   | LocalDate | Not Null, auto-set |
| returnDate | LocalDate | Nullable           |
 
---

## 🚀 Endpoints

### 📖 Book `/book`

| Method | Endpoint                      | Description                         |
| ------ | ----------------------------- | ----------------------------------- |
| GET    | `/book`                       | Get all books                       |
| GET    | `/book/{id}`                  | Get book by ID                      |
| POST   | `/book`                       | Create a new book                   |
| PUT    | `/book/{id}`                  | Update a book                       |
| PATCH  | `/book/{id}`                  | Partial update a book               |
| DELETE | `/book/{id}`                  | Delete a book                       |
| GET    | `/book/search?author=&title=` | Search books by author and/or title |

### 👤 Member `/member`

| Method | Endpoint         | Description                           |
| ------ | ---------------- | ------------------------------------- |
| GET    | `/member`        | Get all members                       |
| GET    | `/member/{id}`   | Get member by ID                      |
| POST   | `/member`        | Create a new member                   |
| PUT    | `/member/{id}`   | Update a member                       |
| PATCH  | `/member/{id}`   | Partial update a member               |
| DELETE | `/member/{id}`   | Delete a member                       |
| GET    | `/member/search` | Search members by name, city or phone |

### 📋 Loan `/loan`

| Method | Endpoint                         | Description                |
| ------ | -------------------------------- | -------------------------- |
| GET    | `/loan`                          | Get all loans              |
| GET    | `/loan/{id}`                     | Get loan by ID             |
| GET    | `/loan/member/{memberId}`        | Get loans by member        |
| GET    | `/loan/book/{bookId}`            | Get loans by book          |
| GET    | `/loan/member/{memberId}/active` | Get active loans by member |
| POST   | `/loan`                          | Create a new loan          |
| PATCH  | `/loan/{id}/return`              | Return a book              |
| DELETE | `/loan/{id}`                     | Delete a loan              |
 
---

## 🔒 Security Rules

**Public (no login required):**

- `/login.html`
- `/components/**`
- `/js/**`
- `/css/**`
- `/images/**`
- `/index.html`, `/books.html`, `/members.html`, `/loans.html`
  **Protected (login required):**

- `/book/**`
- `/member/**`
- `/loan/**`
---

## ▶️ Getting Started

### Prerequisites

- Java 21
- MySQL 8.0
- Maven
### Database Setup

```sql
CREATE DATABASE library_db;
```

Then create a MySQL user or use your `root` credentials. Make sure MySQL is running on port `3307` (or update the config accordingly).

### Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/library_db
spring.datasource.username=root
spring.datasource.password=your_password
 
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run the project

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`.  
You will be redirected to `/login.html` if not authenticated.
 
---

## 📄 API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI spec (JSON) is available at:

```
http://localhost:8080/v3/api-docs
```
 
---

## 📦 Example Requests

### Create a Book

```http
POST /book
Content-Type: application/json
 
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "publisher": "Prentice Hall"
}
```

### Create a Loan

```http
POST /loan
Content-Type: application/json
 
{
  "bookId": 1,
  "memberId": 2
}
```

### Return a Book

```http
PATCH /loan/1/return
```
 
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

| Status | Description                    |
| ------ | ------------------------------ |
| 400    | Bad Request / Validation Error |
| 401    | Unauthorized                   |
| 404    | Resource Not Found             |
| 500    | Internal Server Error          |
 
---

## 📝 License

This project is licensed under the [MIT License](LICENSE).
 