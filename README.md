# Spring Boot Project
This repository is a **base template** for a Spring Boot application, designed to demonstrate common real-world practices, including:

- **Layered architecture** (domain, service, controller, etc.)
- **Correlation ID** for logging
- **API versioning** (via custom `@Version` annotation)
- **Global exception handling** + consistent `BaseResponse` format
- **Bean Validation** for request DTOs
- **Logging** with environment-specific configuration using `logback-spring.xml`
- **GitHub Actions CI** pipeline (run unit, integration, E2E tests)
- **Git Hook** (`pre-commit`) to run tests locally before committing
- **Dockerfile** for building a Docker image
- **Example unit, integration, and E2E tests** (using JUnit 5, Mockito, RestAssured)