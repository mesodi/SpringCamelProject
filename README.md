# Spring Camel Project

This is a spring Boot project that integrates Apache Camel for routing and communication with external servers.

The application also includes user authentication using Amazon Cognito.

# Project Structure

The project is organised into packages, each serving specific purpose:

- es.wacoco.SpringCamelProject.Auth.Cognito: Handles Amazon Cognito authentication with classes for Aws credentials and error handling.

- es.wacoco.SpringCamelProject.Auth.Controller: Manages user authentication endpoints using spring MVC.

- es.wacoco.SpringCamelProject.Auth.error: Contains custom exceptions for handling errors related to user registration and authentication.

- es.wacoco.SpringCamelProject.Auth.Service: Implements the business logic for user registration,confirmation,and sign-in using Amazon Cognito.

- es.wacoco.SpringCamelProject.Camel.Controller: Provides Rest endpoints for initiating Camel routes related to different servers.

- es.wacoco.SpringCamelProject.Camel.Route: Defines Camel routes for communication with external servers and processing data.

- es.wacoco.SpringCamelProject.Camel.Service: Implements Camel service logic,triggering Camel routes and handling responses.

- es.wacoco.SpringCamelProject.Main: Main class to run the spring Boot application.
# Run Steps

- Spring boot Starts up  WireMock using docker compose to simulate external service for the camel routes
 
**Access the authentication endpoints:** 

/register: Register a new user.

/confirm:Confirm user registration.

/login: User login.

/home:Home page after successful login.


Access Camel endpoints:
/Police: Fetch data from the police server.

/company: Fetch data from the company server.

/contact: Fetch data from the contact server.

/criminal: Fetch data from the criminal server.

/qualifiedApplicant: Trigger a Camel route for processing qualified applicants.

# Dependencies

Spring Boot: Framework for building java-based enterprise applications.

APache Camel: Integration framework for routing and mediation rules.

Amazon Cognito SDK : AWS SDK for interacting with Amazon Cognito services.



# Requirements
- Jdk 21
- Docker
- IDEA, for java



