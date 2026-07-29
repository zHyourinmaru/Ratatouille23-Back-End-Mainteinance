# Ratatouille23-Back-End-Mainteinance
Ratatouille23 is a Spring Boot backend application designed for restaurant management (handling dishes, allergens, orders, tables, and users) . This repository showcases a complete Software Evolution and Re-engineering cycle. 

***

# Ratatouille23 - Backend Modernization & Software Evolution

## Project Overview
**Ratatouille23** is a Spring Boot backend application designed for restaurant management, handling dishes, allergens, orders, tables, and users. 

This repository showcases a complete **Software Evolution and Re-engineering** cycle. The primary goal was not merely adding new horizontal features, but taking a functionally indebted "legacy" codebase and systematically curing its technical debt. By applying advanced concepts from the **Software Project Management and Evolution (SPME)** course, the system was transformed into a scalable, highly cohesive, and cloud-ready enterprise architecture.

## Tech Stack
* **Core:** Java 17, Spring Boot 3.1.5, Spring Security (JWT),.
* **Database:** PostgreSQL (Production/Staging), H2 In-Memory (Testing),.
* **Code Generation:** MapStruct, Lombok.
* **Testing:** JUnit 5, Mockito, Hamcrest, Apache JMeter (Performance/Load Testing),.
* **DevOps & CI/CD:** Docker, Docker Compose, Jenkins (Declarative Pipeline), Maven,.
* **Quality Assurance:** JaCoCo (Coverage), Checkstyle (Static Analysis).

---

## Key Engineering Highlights

### 1. SOLID Architecture & Refactoring
The legacy AS-IS codebase suffered from tight coupling and low cohesion. The architecture was refactored by rigorously applying **SOLID** principles:
* **Dependency Inversion Principle (DIP):** REST Controllers were decoupled from concrete services by extracting and injecting 6 interfaces (e.g., `IOrderService`, `IAllergenService`),,. This enabled pure isolated unit testing via Mockito and drastically reduced the *Coupling Between Objects (CBO)* metric,.
* **Single Responsibility Principle (SRP):** Eradicated "God Objects" and duplicated logic (e.g., merging `addAllergen` and `createAllergen`), which dropped the *Lack of Cohesion in Methods (LCOM)* metric and halved the *Halstead Volume*,,.
* **Liskov Substitution Principle (LSP):** Fixed generic `RuntimeException` swallowing in the global `CustomExceptionHandler`, restricting it to specific exceptions (like `AccessDeniedException`) to restore proper error predictability and HTTP semantic responses,.

### 2. MapStruct Migration (Perfective Maintenance)
Removed heavily duplicated, error-prone manual mapping classes (boilerplate `Function<T, R>` mappers) in favor of **MapStruct**,. This *Perfective Maintenance* intervention shifted the discovery of mapping bugs from Runtime to Compile-time, improving type-safety and drastically reducing the Lines of Code (LOC),.

### 3. Software Metrics & Functional Size
All refactoring interventions were mathematically validated using structural and object-oriented metrics.
* **Function Point Analysis (FPA):** The functional size of the backend was measured at **81 Unadjusted Function Points**. This metric remained completely identical before and after the refactoring, proving that the structural changes did not alter the user-perceived functionalities,.
* **Maintainability Index (MI) & CK Suite:** The *Chidamber & Kemerer (CK) Suite* was used to monitor the design quality, successfully lowering WMC (Weighted Methods per Class) and CBO,.

### 4. Advanced Quality Assurance & Clean Tests
* **Clean Testing:** Test suites were completely rewritten following the **AAA (Arrange, Act, Assert)** pattern,. Standard assertions were replaced with **Hamcrest** semantic matchers to improve readability and *Program Comprehension*,.
* **Test Isolation:** Introduced an `application-test.properties` profile utilizing an **H2 In-Memory Database** to eliminate side effects and flaky tests caused by local database dependencies,,.
* **Metamorphic Testing:** To overcome the historical **Oracle Problem** in highly dynamic domains (e.g., real-time order pricing with changing allergens), we tested the invariance of *Metamorphic Relations* instead of hardcoding absolute expected outputs,.

### 5. DevOps, CI/CD Pipeline & "Intelligence in the Loop"
A robust **10-stage Declarative Jenkins Pipeline** (`Jenkinsfile`) was developed to fully automate the Continuous Integration and Continuous Delivery (CD) lifecycle,,. 
* **The Deterministic Filter:** During development, **Large Language Models (LLMs)** were used as pair-programmers to accelerate test generation and code abstraction,. To mitigate their innate probabilistic risks (e.g., *Hallucinated Objects* and *Missing Corner Cases*), the Jenkins pipeline acted as an infallible **"Deterministic Filter"**,,. The build strictly failed if JaCoCo coverage dropped or if Checkstyle detected violations, physically blocking AI hallucinations from reaching production,.
* **Continuous Performance Testing:** Stage 9 automatically clones a staging environment via `docker-compose.staging.yml` and runs an Apache JMeter load test against it (simulating 50 concurrent users with a 10-second ramp-up time) to prevent performance regressions,,.

### 6. Agile Traceability & Ticketing
The evolution was simulated using an Agile (Scrum) workflow with Jira. Through **Smart Commits** and the `jiraSendBuildInfo` instruction in the Jenkins pipeline, every single code change is traceable to a specific User Story,. This approach successfully eliminated the *Cost of Untraceable Change*, ensuring that every modification is properly documented and verified against the *Definition of Done (DoD)*,,.
