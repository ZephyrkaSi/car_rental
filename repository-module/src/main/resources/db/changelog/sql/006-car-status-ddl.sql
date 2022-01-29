--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:add-car-status-table splitStatements:true endDelimiter:;
CREATE TABLE CAR_RENTAL.CAR_STATUS
(
    ID     INT AUTO_INCREMENT PRIMARY KEY,
    STATUS VARCHAR(10) NOT NULL
);
--rollback DROP TABLE IF EXISTS CAR_RENTAL.CAR_STATUS