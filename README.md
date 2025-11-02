⚡ Spring Boot Caching Application

A Spring Boot Caching Application that demonstrates the use of Redis Cloud for caching frequently accessed data, reducing database load, and improving performance.

The project follows a clean MVC architecture, includes Global Exception Handling, and uses Optimistic Locking (@Version) to handle concurrent updates safely.

🚀 Features
🧠 Redis Caching

Integrated Redis Cloud to cache frequently used data.

Reduces database hits and improves API response time.

Configured TTL (Time-To-Live) and TTI (Time-To-Idle) for cache expiry.

Custom cache key prefix for better organization.

🧩 Optimistic Locking (@Version)

Added @Version field in entities to prevent data overwrites in concurrent updates.

Ensures data consistency when multiple users modify the same record simultaneously.

Throws an exception if an outdated entity tries to update newer data.

🧱 MVC Architecture

Clean separation between Controller, Service, and Repository layers.

Easy to maintain, extend, and test.

🚨 Global Exception Handling

Centralized error handling using @ControllerAdvice and @ExceptionHandler.

Returns structured and meaningful error messages to the client.

☁️ Redis Cloud Integration

Connected to Redis Cloud, a managed Redis service.

Ensures reliable caching without hosting Redis locally.

🛠️ Tech Stack
Backend Framework	Spring Boot
Caching	Redis Cloud
Database	H2 / MySQL
Locking Mechanism	Optimistic Locking (@Version)
Build Tool	Maven
Architecture	MVC
Exception Handling	@ControllerAdvice, @ExceptionHandler
Language	Java
