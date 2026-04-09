# URL Shortener API (Spring Boot)

A minimal, production-quality REST API for shortening URLs using Spring Boot 3, Java 17, and Spring Data JPA.

## Tech Stack
- Java 17+
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 database (default)
- Lombok

## Project Structure
```
src/main/java/com/example/urlshortener/
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
├── service/
└── util/
```

## Run
```bash
mvn spring-boot:run
```

## API Endpoints

### 1) Create Short URL
`POST /api/shorten`

Request body:
```json
{
  "url": "https://example.com",
  "alias": "optional"
}
```

Response (`201 Created`):
```json
{
  "shortUrl": "http://localhost:8080/abc123"
}
```

Possible errors:
- `400 Bad Request` invalid URL / validation failure
- `409 Conflict` duplicate alias

### 2) Redirect
`GET /{code}`

Behavior:
- Increments click counter
- Returns `302 Found` with `Location` header set to original URL

### 3) Stats
`GET /api/stats/{code}`

Response (`200 OK`):
```json
{
  "originalUrl": "https://example.com",
  "clicks": 10,
  "createdAt": "2026-04-09T12:00:00"
}
```

Possible errors:
- `404 Not Found` if code does not exist

## Notes
- Base62 random short code generation (6–8 chars).
- Optional custom alias support.
- Duplicate alias handling.
- Basic URL validation for `http`/`https`.
- Global exception handling with consistent error response.
