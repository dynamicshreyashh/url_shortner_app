
# 🔗 URL Shortener Application

A modern URL Shortener built using **Java Spring Boot** and **Vanilla JavaScript** that allows users to generate short URLs, manage links, track click statistics, and redirect users seamlessly.

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

---

## 🛠️ Tech Stack

| Category         | Technologies               |
| ---------------- | -------------------------- |
| Backend          | Java, Spring Boot          |
| Frontend         | HTML5, CSS3, JavaScript    |
| Database         | H2 Database                |
| ORM              | Spring Data JPA, Hibernate |
| Build Tool       | Maven                      |
| Containerization | Docker                     |
| Deployment       | Render                     |

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

```bash
src
├── main
│   ├── java
│   │   └── urlshortner
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       ├── entity
│   │       └── Application.java
│   │
│   └── resources
│       ├── static
│       │   ├── index.html
│       │   ├── style.css
│       │   └── app.js
│       │
│       └── application.properties
│
└── test
```

---

## 📡 REST API Endpoints

| Method | Endpoint                  | Description              |
| ------ | ------------------------- | ------------------------ |
| POST   | `/api/shorten`            | Create a shortened URL   |
| GET    | `/r/{shortCode}`          | Redirect to original URL |
| GET    | `/api/stats/{shortCode}`  | Retrieve URL statistics  |
| DELETE | `/api/delete/{shortCode}` | Delete a shortened URL   |

---

## ⚙️ Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/dynamicshreyashh/url_shortner_app.git
cd url_shortner_app
```

### 2️⃣ Build the Project

```bash
mvn clean package
```

### 3️⃣ Run the Application

```bash
java -jar target/urlshortner-0.0.1-SNAPSHOT.jar
```

### 4️⃣ Access the Application

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

* User Authentication & Authorization
* Custom Alias Support
* QR Code Generation
* Analytics Dashboard
* PostgreSQL/MySQL Support
* Redis Caching

---

## 👨‍💻 Author

### Shreyash Bhosale

* GitHub: https://github.com/dynamicshreyashh
* Portfolio: https://shreyas-h-portfolio.vercel.app/

---

⭐ If you found this project useful, consider giving it a star on GitHub!
