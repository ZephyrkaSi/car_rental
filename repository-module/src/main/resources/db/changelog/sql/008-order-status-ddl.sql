--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:add-order-status-table splitStatements:true endDelimiter:;
CREATE TABLE CAR_RENTAL_LB.ORDER_STATUS
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    STATUS      VARCHAR(20)  NOT NULL,
    DESCRIPTION VARCHAR(300) NOT NULL
);
--rollback DROP TABLE IF EXISTS CAR_RENTAL_LB.ORDER_STATUS