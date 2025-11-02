<div align="center">

# Spring Boot Caching Application

A **Spring Boot Caching Application** that demonstrates the use of **Redis Cloud** for caching frequently accessed data, reducing database load, and improving performance.  

The project follows a clean **MVC architecture**, includes **Global Exception Handling**, and uses **Optimistic Locking (`@Version`)** to handle concurrent updates safely.  

</div>

---
## 🚀 Features

### ⚙️ **Redis Caching**
- Integrated **Redis Cloud** to cache frequently used data.  
- Reduces **database hits** and improves **API response time**.  
- Configured **TTL (Time-To-Live)** and **TTI (Time-To-Idle)** for cache expiry.  
- Added **custom cache key prefix** for better organization.  

---

### 🧩 **Optimistic Locking (`@Version`)**
- Added `@Version` field in entities to prevent **data overwrites** in concurrent updates.  
- Ensures **data consistency** when multiple users modify the same record simultaneously.  
- Throws an **exception** if an outdated entity tries to update newer data.  

---

### 🧱 **MVC Architecture**
- Clean separation between **Controller**, **Service**, and **Repository** layers.  
- Easy to **maintain**, **extend**, and **test**.  

---

### 🚨 **Global Exception Handling**
- Centralized error handling using `@ControllerAdvice` and `@ExceptionHandler`.  
- Returns **structured** and **meaningful error messages** to the client.  

---

### 🛠️ **Tech Stack**

Backend Framework: Spring Boot
Cache Management: Redis Cloud (Spring Data Redis)
Architecture: MVC (Model-View-Controller)
Database: H2 / MySQL
Concurrency Control: Optimistic Locking (@Version)
Exception Handling: @ControllerAdvice, @ExceptionHandler
Build Tool: Maven
Language: Java

