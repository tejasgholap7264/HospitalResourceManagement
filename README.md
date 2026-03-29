# 🏥 Hospital Resource Management System

## 🔹 Overview
The Hospital Resource Management System is a backend application built using Java and Spring Boot to manage hospital operations such as patient records, doctor management, and appointment scheduling.

The system is designed with a layered architecture to ensure scalability, maintainability, and clean separation of concerns, making it suitable for real-world backend applications.

---

## 🔹 Architecture

This project follows a **Layered Architecture**:

Client → Controller → Service → Repository → Database

### Components:
- **Controller Layer** → Handles HTTP requests and responses  
- **Service Layer** → Contains business logic and validations  
- **Repository Layer** → Handles database interactions using JPA  
- **Database Layer** → Stores application data (MySQL)  

### Key Characteristics:
- Loosely coupled components  
- Clean separation of concerns  
- Scalable backend structure  
- RESTful communication  

---

## 🔹 Features

- Patient registration and management  
- Doctor and staff management  
- Appointment scheduling system  
- CRUD operations for all entities  
- Structured API design  
- Efficient data handling using relational database  

---

## 🔹 Tech Stack

- **Backend:** Java, Spring Boot  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **API Testing:** Postman  
- **Architecture:** RESTful Services  

---

## 🔹 Project Structure

src/main/java/com/example/

- controller       → REST Controllers  
- service          → Business logic  
- repository       → Database access (JPA)  
- model/entity     → Data models  
- exception        → Exception handling  

---

## 🔹 API Endpoints

### 🧑 Patient APIs
- POST /patients → Add new patient  
- GET /patients → Get all patients  
- GET /patients/{id} → Get patient by ID  
- PUT /patients/{id} → Update patient  
- DELETE /patients/{id} → Delete patient  

---

### 👨‍⚕️ Doctor APIs
- POST /doctors → Add doctor  
- GET /doctors → Get all doctors  
- GET /doctors/{id} → Get doctor by ID  
- PUT /doctors/{id} → Update doctor  
- DELETE /doctors/{id} → Delete doctor  

---

### 📅 Appointment APIs
- POST /appointments → Book appointment  
- GET /appointments → Get all appointments  
- GET /appointments/{id} → Get appointment  
- DELETE /appointments/{id} → Cancel appointment  

---

## 🔹 How It Works (Flow)

1. Client sends request  
2. Controller handles request  
3. Service layer processes logic  
4. Repository interacts with database  
5. Response is returned to client  

---

## 🔹 Getting Started

### Prerequisites
- Java 8+  
- Maven  
- MySQL  

---

### Installation

```bash
git clone https://github.com/tejasgholap7264/HospitalResourceManagement.git
cd HospitalResourceManagement

Run the Application
mvn spring-boot:run

🔹 Future Enhancements 🚀
Implement JWT Authentication
Add Role-Based Access Control (Admin / Doctor / Patient)
Integrate Spring Security
Add API validation and global exception handling
Dockerize the application
Integrate frontend using React
Add logging and monitoring (ELK / Prometheus)
Implement caching (Redis) for performance optimization
Add Swagger/OpenAPI documentation

🔹 Author
Tejas Gholap
