# Your Car Your Way messaging PoC

## Goal

The goal of this messaging PoC is to prouve the feasibility of the instant messaging fonctionnality, part of YourCarYourWay web application.

## Software developpment

### Front-End

Generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.2.0.

### Back-End

Generated with maven 4.0 and developped with Spring Boot 3.2.

### Database

Generated with MySQL 8.1.

## Ressources

### MySQL

SQL script for creating the schema is available in `back/src/main/ressources/sql` folder.

## Environnement variables

The database credentials are initialized in environnement variables using `YCYW_DB_USER` and `YCYW_DB_PW` params.

The JWT secret key is a 64 characters key and initialized in environnement variable using `JWT_SK` param. An example of key would be `5486921375896201458967236589124758692384105687951203567491287536`.

## Start the project

### Database

Create database using the SQL script. Next, create a database user account :
> create user 'name_of_user'@'%' identified by 'password_of_user';
> grant all on ocow_db.* to 'name_of_user'@'%';

### Back-End

Launch .jar file inside `back/target`
> java -jar -Dspring.datasource.username=name_of_user -Dspring.datasource.password=password_of_user -Djwt.secretKey=64_chars_secret_key back-0.0.1-SNAPSHOT.jar

### Front-End

Go inside folder:
> cd front

Install dependencies:
> npm install

Launch Front-end in developpment environnement:
> ng serve;

After lauchning, go to `http://localhost:4200/` and create a client user account. After account creation, you will be redirected to the client user login page to log in. Next, create a new discussion by choosing the subject and click on the create button. This redirect to the chat and then you can start to write messages.

In the same time, go to `http://localhost:4200/login/support`. Log to the support user account with email : `support@ycyw.com` and password : `Support!Test123`. Next, click on a discussion and start to chat with client user.







