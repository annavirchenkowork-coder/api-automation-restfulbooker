# API Automation – Restful Booker (Java + RestAssured)
A clean, modular API automation framework built using **Java**, **RestAssured**, **JUnit 5**, and **Maven**, designed to test the public **Restful-Booker** API.

This project demonstrates real-world API testing practices including:
- Scalable framework structure
- Reusable API client classes
- Request specifications
- Positive & negative test scenarios
- JSON body validation
- Data-driven request bodies
- Clear, maintainable test design

## Tech Stack
- **Java  17**
- **RestAssured 5**
- **JUnit 5 (Jupiter)**
- **Maven**
- **IntelliJ IDEA**

## Project Structure
```
src
 └─ test
     └─ java
         └─ com.netta.restfulbooker
             ├── base
             │     └── BaseTest.java            # Shared setup: base URI, content type, RequestSpec
             │
             ├── health
             │     └── HealthCheckTest.java     # /ping endpoint tests
             │
             └── booking
                   ├── BookingApi.java          # API client handling POST/GET requests
                   └── BookingTests.java        # Booking test scenarios
                  

 ```

## Features
### Shared Request Specification

Centralized in `BaseTest` using `RequestSpecBuilder` for consistent request configuration.

### API Client Pattern

All HTTP calls are handled inside `BookingApi` to keep tests clean and readable.
 ### Health Check

Simple test to verify API is alive (/ping returns 201).

### Booking Tests
- Create booking (POST)
- Validate response fields
- Extract `bookingid`
- Future: retrieve booking, update, delete, negative cases

## How to Run Tests
Run all tests:
```sh
mvn test
```
Or directly from IntelliJ (or any other preferred editor)

## Environment Setup
This project is fully set up and ready to run. To explore or test it on your own device:

### 1. Clone the Repository
```sh
git clone https://github.com/<your-username>/api-automation-restfulbooker.git
```

### 2. Open the Project in Your IDE
Navigate to the cloned folder and open it in IntelliJ IDEA or any preferred editor.

## What’s Next (Future Enhancements)
- Token-based auth (/auth)
- PUT / PATCH / DELETE booking tests
- Negative tests & boundary scenarios
- JSON schema validation
- Test data builders
- Environment configs
- GitHub Actions CI pipeline
- Allure reporting integration

## 🌱 Git Workflow
A simple, clean branching strategy is recommended:
### 1. Clone the repo
```sh
git clone https://github.com/<your-username>/api-automation-restfulbooker.git
```
### 2. Create a new branch (for enhancements, fixes, or new tests)
```sh
git checkout -b feature/your-feature-name
```
### 3. Commit your changes
```sh
git add .
git commit -m "Add new feature test for payment flow"
```

### 4. Push to GitHub
```sh
git push origin feature/your-feature-name
```

### 5. Open a Pull Request
Submit a PR to the develop branch once your feature or fix is ready for review.

## Final Note & Contributions
Feel free to fork the repo and submit pull requests!
This framework is intentionally simple — perfect for beginners learning API automation.
