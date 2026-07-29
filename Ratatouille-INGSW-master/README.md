# Ratatouille 23

Ratatouille 23 is a software developed to offer support in
management and operation of catering activities.
This repository contains the backend + Spring Boot RESTful API used by the application written in Flutter.


## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Docker](#docker)
- [License](#license)
  
    
## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 17 or higher installed.
- Apache Maven installed.
- Your favorite REST API client (e.g., Postman or curl).

## Installation

Follow these steps to set up and run the project:

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/Nokacu44/Ratatouille-INGSW.git

2. Navigate to the project directory:

   ```bash
   cd Ratatouille-INGSW

3. Build the project using Maven:
   ```bash
   mvn clean install

## Usage
To run the Spring Boot application, execute the following command:
  ```bash
  java -jar Ratatouille23-0.0.1-SNAPSHOT.jar
  ```
The application will start, and you can access it at http://localhost:8080


## Endpoints
The API exposes a lot of different endpoints based on the resources avaible (*allergen*, *dish*, *order*, *table*, *user*) and they all follow the same url conventions
The folowing endpoints are for managing the allergens:
- **GET /api/v1/allergen**: returns all the allergens in the DB.
#### Example Response
```json
{
  "id": 1,
  "name": "Gluten",
  "dishes": ["Margherita"]
}
```
- **GET /api/v1/search{?id}{?name}{?dishes}**: search for allergen based on optional name or dishes that contains them, if the id is provided the corresponding allergen is returned.
-   **POST /api/v1/{allergenId}**: update an allergen (requires a json body with all informations).
#### Example
```json
{
  "name": "Gluten",
}
```
- **DELETE /api/v1/{allergenId}**: delete an allergen.
- **PUT /api/v1/{allergenId}**: update an allergen (requires a json body with all informations).
####  Example
```json
{
  "name": "Gluten",
  "dishes": ["Margherita","Pasta"],
}
```

## Docker
You can also run the application using Docker. Follow these steps:

1. Build the Docker image:
```bash
  docker build -t Ratatouille-INGSW .
```

2. Run the Docker container:
```bash
  docker run -p 8080:8080 Ratatouille-INGSW
```
The application will start, and you can access it at http://localhost:8080









   
