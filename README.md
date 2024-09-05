# NTD Software - Arithmetic Calculator REST API

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Problem Description](#problem-description)
    - [Operation Costs](#operation-costs)
    - [Initial User Balance](#initial-user-balance)
5. [Running the Application](#running-the-application)
    - [Prerequisites](#prerequisites)
    - [Docker Setup](#docker-setup)
6. [Swagger UI](#swagger-ui)
7. [Authentication](#authentication)
    - [JWT Example Sign Up](#jwt-example-sign-up)
    - [JWT Example Login](#jwt-example-login)
8. [Database](#database)
9. [Notes](#notes)

## Overview

This project is developed for **NTD** as part of an evaluation. The goal is to implement a web platform that provides basic calculator functionalities such as addition, subtraction, multiplication, division, square root calculation, and random string generation. Each functionality is assigned a specific cost per request, which can be configured. The platform ensures that the user's balance is deducted based on the cost of each operation.

## Features

- Basic calculator operations (Addition, Subtraction, Multiplication, Division, Square Root)
- Random string generation (uses an external API)
- JWT-based authentication for securing API requests
- User balance management
- Pre-populated `Operation` table for available operations

## Technologies Used

- **Java 21** - Backend logic
- **Spring Boot 3.2.9** - Framework for building the application
- **Spring Security** - Provides JWT authentication
- **JPA (Java Persistence API)** - Database interactions
- **Lombok** - Reduces boilerplate code
- **PostgreSQL** - Database
- **Docker** & **Docker Compose** - Containerization
- **Maven** - Dependency management and build automation

## Problem Description

The platform offers simple calculator functionality where each operation is assigned a specific cost per request. The user balance is deducted based on the cost of the performed operation.

The operations included in this platform are:

- Addition
- Subtraction
- Multiplication
- Division
- Square Root
- Random String Generation

## Operation Costs
Each operation has an associated cost, which is deducted from the user's balance.

- Addition: $10.00
- Subtraction: $10.00
- Multiplication: $15.00
- Division: $20.00
- Square Root: $25.00
- Random String Generation: $30.00

### Initial User Balance
When a user is created, they start with an initial balance of $100.00.

## Running the Application

### Prerequisites

Ensure you have the following installed:

- Docker
- Docker Compose
- Java 17 or higher
- Maven

### Docker Setup


This project is set up to run easily using Docker and Docker Compose. To run the application locally:

1. Clone the repository:

   ```bash
    git clone https://github.com/your-repo/calculator-api.git
    cd calculator-api
   
2. Create an '.env' file at the root of the project with the following content: 

   ```bash
    DATABASE_URLCON=jdbc:postgresql://db:5432/api_db
    DATABASE_USERNAME=postgres
    DATABASE_PASSWORD=postgres
    APP_SECRET_KEY=7uHZGR5ypNyM3o332NTqotDjbNQibLzVV+BUvEc3UhU=
    APP_EXPIRATION_JWT=86400000
    APP_RANDOM_STRING_URI=https://api.random.org/json-rpc/4/invoke
    APP_RANDOM_STRING_SECRET_KEY=<YOUR_API_KEY>
   

> Make sure you have those properties filled
> otherwise it won't be possible to start the application
> - **APP_SECRET_KEY**: You must have any secret key for JWT (H256), you can use the example above.
> - **APP_RANDOM_STRING_URI**: It is the API that the project is going to consume to generate random string
> - **APP_RANDOM_STRING_SECRET_KEY**: You need to have an account and use your api_key in order to make a request.
> - **APP_EXPIRATION_JWT**: The expiration time for the jwt token (1 day)


3. Start the application using Docker Compose:


    ```bash
    docker-compose up --build

This will build the application and start both the API and the PostgreSQL database. The **Operation** table will be pre-populated with the available operations (addition, subtraction, multiplication, etc.) to avoid the need for manual entries.


### Docker File
The Dockerfile builds a Maven project and creates a lightweight image to run the Spring Boot application

### Docker Compose File
The docker-compose.yml file includes services for the database, application, and migration setup

### Swagger UI
Swagger provides an interactive interface where you can test the API endpoints. To explore the API using Swagger UI, visit:

    /api/v1/swagger-ui.html


## Authentication
The application uses JWT (JSON Web Token) for authentication. After logging in, a token is generated and must be included in the Authorization header for all subsequent requests. The system does not currently store or manage tokens, which means there is no token refresh or logout functionality. In a real-world scenario, token storage and invalidation should be implemented to allow proper user logout.

### JWT Example Sign up
1. Sign up to register and get a token

       POST /api/v1/auth/register
2. The payload must be:
   
       {
          "username": "youremail@email.com",
          "password": "yourpassword"
       }

### JWT Example Login
1. Login to obtain a token
   
       POST /api/v1/auth/login

   
2The payload must be:
    
       {
          "username": "youremail@email.com",
          "password": "yourpassword"
       }
   
All others resources are protected. That means if you want to access any resource that is protected you must include your bearer token into the request.

    Authorization: Bearer <your-token>

## Database
- The application uses PostgreSQL to store user data, operation history, and other relevant information.
- A migration script automatically sets up the Operation table, which holds all the available operations a user can perform (addition, multiplication, etc.), eliminating the need to manually configure them.


## Notes
- The JWT authentication is basic for this project and doesn't handle token storage or refresh. A real-world implementation would require those features.
- The random string generation uses an external API (random.org), and its key is passed via the environment variable APP_RANDOM_STRING_SECRET_KEY.
- The project includes migration scripts to automate database setup for easier local development