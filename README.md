Take Home Assessment for Senior Backend Developer (Java)
Objective
The objective of this project is to develop the backend API for a simplified Task Management application using Java and Spring Boot. The purpose of the assignment is to evaluate your ability to design and implement a basic backend API solution within a limited timeframe.

Focus is on delivering a working backend with reasonable functionality rather than striving for perfection. The project is expected to be submitted within one week.

Functional Requirements
This backend API should support the following functionalities:

User Management

Create User (Sign Up): Enable users to register by providing a username, email, and password.
Task Management

Create Task: Allow a user to create a task with appropriate fields such as title, description, and due date.
List All Tasks: Provide a way to list all tasks created by the user.
Update Task: Enable the user to update fields of an existing task.
Delete Task: Allow the user to delete a task.
Authentication/Authorization

Use JWT for user authentication and securing API endpoints.
Users need to authenticate to perform task-related operations.
Technical Requirements
Java Framework: The backend API is developed using Spring Boot.
Database: Use an in-memory database (e.g., H2) to store user and task data for simplicity.
Modular Design: Code should be well-structured, using layers like Controller, Service, and Repository to ensure clean and maintainable architecture.
Version Control: Use GitHub for version control. Commits should have meaningful messages that reflect the changes made.
Microservices Architecture
This project consists of 4 microservices with service discovery and API routing. The services should be run in the
following order:

Eureka Server: Service discovery to register all microservices.
API Gateway: Routes client requests to the appropriate service.
User Service: Handles user creation and authentication.
Task Service: Manages tasks (creation, retrieval, updating, and deletion).

NB: Please the following video for testing purposes:
https://drive.google.com/file/d/1liljZPZ1zUmpgWico_vQTE_Fd753Qbin/view?usp=sharing

