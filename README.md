# Сar rental project

This is the final project of a java enterprise development course.

Project: Web multi-module Application with GUI for renting cars.

---

## Technologies

<ol>
<li>Java version: 11</li>
<li>Maven</li>
<li>Multi module project</li>
<li>Spring boot version: 2.4.2 (including Slf4j + Logback, Jackson)</li>
<li>Spring data JPA</li>
<li>REST Controller</li>
<li>MySQL database</li>
<li>Lombok</li>
<li>Hikari connection pool</li>
<li>Liquibase</li>
<li>JUnit 5 with Mockito</li>
<li>Testcontainers</li>
<li>Docker</li>
</ol>

___

## Setup

- Install Java SE Development Kit 11
- Install Maven
- Install Docker and run the following command **docker-compose up -d**
- Build the project with maven with the following command **mvn clean install**
- Go to **web-module/target** directory and run the following command **java -jar web-module-1.0-SNAPSHOT.jar**
- Go to the address in your browser **localhost:8080**
