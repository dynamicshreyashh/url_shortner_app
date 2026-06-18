# 🔗 URL Shortener Application

A modern URL Shortener built using **Java Spring Boot** and **Vanilla JavaScript** that allows users to generate short URLs, manage links, track click statistics, and redirect users seamlessly.

The project includes an automated **CI pipeline using GitHub Actions** for build validation, testing, packaging, and artifact generation.

---

## 🚀 Live Demo

🔗 https://url-shortner-app-wwja.onrender.com/

---

## 📸 Application Preview

<img width="1786" height="829" alt="URL Shortener Application" src="https://github.com/user-attachments/assets/fdd9f4c1-b12a-46ec-b206-7c1c1ce491ca" />

---

## ✨ Features

* 🔗 Generate short URLs instantly
* 📊 Track total clicks for each URL
* ⏰ Configure URL expiration duration
* 📈 View detailed URL statistics
* 🗑️ Delete shortened URLs
* ↪️ Automatic redirection to original URLs
* 📱 Fully responsive user interface
* ⚡ Fast and lightweight application
* ⚙️ Automated CI pipeline using GitHub Actions

---

## 🛠️ Tech Stack

| Category         | Technologies               |
| ---------------- | -------------------------- |
| Backend          | Java, Spring Boot          |
| Frontend         | HTML5, CSS3, JavaScript    |
| Database         | H2 Database                |
| ORM              | Spring Data JPA, Hibernate |
| Build Tool       | Maven                      |
| CI/CD            | GitHub Actions             |
| Containerization | Docker                     |
| Deployment       | Render                     |

---

## ⚙️ CI/CD Pipeline

This project uses **GitHub Actions** to automate validation, testing, packaging, and artifact generation.

### Workflow Location

```text
.github/workflows/ci.yml
```

### Workflow Steps

```text
Push / Pull Request
        │
        ▼
GitHub Actions
        │
        ├── Checkout Repository
        ├── Setup Java 17
        ├── Validate Maven Project
        ├── Run Unit Tests
        ├── Package Application
        ├── Verify JAR Generation
        └── Upload Build Artifact
```

### Pipeline Features

* Automated build validation on every push and pull request
* Unit testing using Spring Boot Test and JUnit
* Maven package generation
* Build artifact storage
* Continuous Integration (CI) using GitHub Actions

---

## 🏗️ System Architecture

```text
User
  │
  ▼
Frontend (HTML, CSS, JavaScript)
  │
  ▼
Spring Boot REST API
  │
  ├── URL Generation
  ├── Click Tracking
  ├── URL Statistics
  └── URL Deletion
  │
  ▼
H2 Database
```

---

## 📁 Project Structure

```text
url_shortner_app
│
├── .github
│   └── workflows
│       └── ci.yml
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── urlshortner
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── entity
│   │   │       ├── exception
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── Application.java
│   │   │
│   │   └── resources
│   │       ├── static
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── app.js
│   │       │
│   │       └── application.properties
│   │
│   └── test
│       └── java
│           └── urlshortner
│               └── ApplicationTests.java
│
├── Dockerfile
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
└── .gitignore
```

---

## 📡 REST API Endpoints

| Method | Endpoint                  | Description              |
| ------ | ------------------------- | ------------------------ |
| POST   | `/api/shorten`            | Create a shortened URL   |
| GET    | `/r/{shortCode}`          | Redirect to original URL |
| GET    | `/api/stats/{shortCode}`  | Retrieve URL statistics  |
| DELETE | `/api/delete/{shortCode}` | Delete shortened URL     |

---

## ⚙️ Getting Started

### Clone Repository

```bash
git clone https://github.com/dynamicshreyashh/url_shortner_app.git
cd url_shortner_app
```

### Build Project

```bash
mvn clean package
```

### Run Application

```bash
java -jar target/urlshortner-0.0.1-SNAPSHOT.jar
```

### Access Application

```text
http://localhost:8080
```

---

## 🐳 Docker Setup

### Build Docker Image

```bash
docker build -t url-shortener .
```

### Run Docker Container

```bash
docker run -p 8080:8080 url-shortener
```

Application will be available at:

```text
http://localhost:8080
```

---

## 🎯 Future Enhancements

* Redis Caching for improved URL retrieval performance
* PostgreSQL integration for production-grade persistence
* Apache Kafka for asynchronous click tracking and analytics

---

## 👨‍💻 Author

### Shreyash Bhosale

GitHub: https://github.com/dynamicshreyashh

Portfolio: https://shreyas-h-portfolio.vercel.app/

---

⭐ If you found this project useful, consider giving it a star on GitHub!
