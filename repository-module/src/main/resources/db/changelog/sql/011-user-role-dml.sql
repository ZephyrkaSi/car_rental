--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:populate-user-role-table splitStatements:true endDelimiter:;
INSERT INTO CAR_RENTAL.USER_ROLE (ROLE)
VALUES ('ADMIN');
INSERT INTO CAR_RENTAL.USER_ROLE (ROLE)
VALUES ('CLIENT');