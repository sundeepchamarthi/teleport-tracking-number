# 🚀 Teleport Tracking Number Service

This Spring Boot microservice provides a high-performance API to generate **16-character unique tracking numbers** with metadata like customer ID, country origin/destination, and weight. Designed for **concurrent access**, **input validation**, and **database integrity**.

---

## 📑 Features

- Unique 16-character tracking number generation (base36 format).
- Validation of country codes (min 2 characters).
- Weight validation up to 2 decimal places.
- Duplicate prevention with DB constraint handling.
- Global exception handling (400, 409, 500).
- Swagger UI enabled for API documentation.
- Docker + Kubernetes ready
---

## 🔧 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- H2 / MySQL (configurable)
- Maven
- Swagger OpenAPI
- Docker
- Kubernetes
- JUnit 5 + Mockito for testing
- Lombok for concise code
---

## 📁 Project Structure

src/
├── main/
│ ├── java/com/teleport/TrackingNumberService/
│ │ ├── controller/
│ │ ├── service/
│ │ ├── util/
│ │ ├── exception/
│ │ └── repository/
│ └── resources/
│ ├── application.properties
│ └── data.sql
├── test/
│ └── java/...TrackingNumberServiceImplTest.java


---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17
- Maven 3.6+
- MySQL (optional; defaults to H2 for demo)

### 📥 Clone & Build
https://github.com/sundeepchamarthi/teleport-tracking-number.git
cd teleport-tracking-number-service
mvn clean install

### 🧪 Run the Application

mvn spring-boot:run

java -jar target/teleport-tracking-number-service-0.0.1-SNAPSHOT.jar


📘 API Usage
📌 Generate Tracking Number
POST /next-tracking-number

✅ Request Body

{
  "origin_country_id": "MY",
  "destination_country_id": "SG",
  "weight": 2.345,
  "customer_id": "4edbb321-14f5-4ec8-b36c-3de947c2a1e6",
  "customer_name": "John Doe",
  "customer_slug": "john-doe"
}
✅ Success Response (200)

{
  "tracking_number": "00D95A0F0000XXXX",
  "created_at": "2025-06-05T15:34:21.134+08:00",
  "customer_id": "...",
  "customer_name": "...",
  "origin_country_id": "...",
  "destination_country_id": "..."
}
### 📝 Error Handling
❗ Error Handling
Status	Description
400	Bad request / Validation failed
409	Duplicate tracking number conflict
500	Internal server/database failure

🔍 Swagger API Docs
Once the app is running:
📌 http://localhost:8080/swagger-ui/index.html


