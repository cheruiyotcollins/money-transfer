# Getting Started

## About Pezesha Money Transfer System

This is a Money Transfer system with JWT authentication and Password encryption using BCrypt Role-based authorization
with Spring Security Customized access denied handling Technologies

Spring Boot 3.0 Spring Security JSON Web Tokens (JWT) BCrypt Maven Getting Started

## Database Setup

In this project MySql db have been used, make sure that this is installed in your machine.
when Mysql server is up, create database schema called `money_transfer_schema` as this is the one that the application
will use and will auto generate all the required tables
The app has two authentication users for easier testing

1. Normal user (username/password=```user/user```)
2. Admin user(username/password=```admin/admin```)
3. After setting up the project as per the guidelines below and your application has started, create user admin and
   user, check swagger documentation for user signup api  (user and admin Roles wil be automatically inserted when
   application starts).
   After signing in using the created user, you will be granted a bearer token which should be used for authentication,
   add to header of every request.

To get started with this project, you will need to have the following installed on your local machine:

## Dependencies

JDK 17+ Maven 3+ To build and run the project, follow these steps:

## Setting up the project

Clone the repository with the command `git clone https://github.com/cheruiyotcollins/money-transfer.git` for https
or ` git@github.com:cheruiyotcollins/money-transfer.git` for ssh
Navigate to the project directory and :

1. Build the project: `mvn clean install -DskipTests` This will build a jar file in target folder
2. If you are running the application as a standalone jar file, be sure to copy `application.properties` into the same
   location as the jar file and run using command `java -jar jarname.jar &`. & is included to run it on background.
3. If you are running the project using an IDE or from command line use: `mvn spring-boot:run` .
4. The application will be available on http://localhost:2023.
5. If you want to build a dockerfile for your project, ensure you have run `step 1` correctly and works without any
   issues. You can then build a docker image using the command `docker build -t lender .`

## Swagger documentation

Make sure the application is up and access it via link the
endpoint `URI/swagger-ui/index.html i.e http://localhost:2023/swagger-ui/index.html`

## Encryption

This is done using library ```jasypt```. Read more about how to do this
here https://medium.com/@javatechie/spring-boot-password-encryption-using-jasypt-e92eed7343ab
You also need to add encryption key in your maven environment by
running ` mvn jasypt:encrypt-value "-Djasypt.encryptor.password=your_secret_key" "-Djasypt.plugin.value=your_password"`.
For now, I have added it to the ```properties.properties```
N.B please note that you will need to to run  `mvn clean "-Djasypt.encryptor.password=your_secrect_key" spring-boot:run`
if you have encrypted your password


## Logging
Spring boot only logs to the console by default. In order to log into a file, the following properties need to be added to the application.properties:

Destination Folder: ```logging.file.path=logs/```
File Name: ```logging.file.name=logs/money_transfer.log ```

## Application Monitoring
In this application Spring Actuator and Grafana, Prometheus and Hypertext Application Language (HAL) is used to monitor the state and health of the application.


To monitor the application all the endpoints are found on this api: `http://localhost:2023/actuator`