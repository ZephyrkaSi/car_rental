--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:add-user-role-table splitStatements:true endDelimiter:;
CREATE TABLE CAR_RENTAL.USER_ROLE
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    ROLE VARCHAR(50) NOT NULL
);

CREATE UNIQUE INDEX IDX_UN_USER_ROLE_ROLE ON CAR_RENTAL.USER_ROLE (ROLE);
--rollback DROP TABLE IF EXISTS CAR_RENTAL.USER_ROLE
