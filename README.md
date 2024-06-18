# propertyviewer

Property Viewer Application
Welcome to the Property Viewer application! This application provides a backend API to manage properties. Below are instructions on how 
to set up, run, interact with the API, and deploy the application using Docker.

Based in the requirements, divided it into major tasks and used Trello as a ticketing tool to keep track
![Screenshot 2024-06-18 090709](https://github.com/sourav-c0des/propertyviewer/assets/32695091/273a4ecc-3425-4638-a226-ce0895ac7f1e)

Tools/Technologies used:
Java 17
SpringBoot
JPA Hibernate
Gradle
PostgreSQL
Postman
IntellijIdea

Installation
Clone the repository:
bash
Copy code
git clone https://github.com/sourav-c0des/propertyviewer.git
cd propertyviewer


Run the main java file
The API will be accessible locally at http://localhost:8080.


Interacting with the API
The Property Viewer application provides the following RESTful endpoints to manage properties:


Add a Building:
Endpoint: POST http://localhost:8080/propertyviewer/add
Body: JSON object with building details (name, street, number, postal code, city, country, description)

List Buildings:
Endpoint: GET http://localhost:8080/propertyviewer/view

Update a Building:
Endpoint: PUT http://localhost:8080/propertyviewer/update
Body: JSON object with updated building details

Delete a Building:
Endpoint: DELETE http://localhost:8080/propertyviewer/delete/

Deploying with Docker
To deploy the Property Viewer application using Docker, follow these steps:

Build the Docker image:
docker build -t testapp_img . 
