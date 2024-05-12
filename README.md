# Todo List Application
 This is a simple Todo List application built with Spring Boot. 
 It allows users to create, manage, and organize their tasks efficiently.

# Features
 Create new tasks with a title and description.
 Assign tasks to specific users (optional).
 Mark tasks as completed.
 Reading To Do list items belong to a specific user

# Technologies Used
 Java 17
 Maven
 Spring Boot 3.2.5
 Spring JPA
 H2 Database

# API Endpoints
 POST /api/todo: Create a new task.
 POST /api/todo/{todoId}/assign/{userId}: Assign a task to a specific user.
 PUT /api/todo/{todoId}/complete: Mark a task as completed.
 GET /api/todo/{userId}: Get all tasks assigned to a specific user.

