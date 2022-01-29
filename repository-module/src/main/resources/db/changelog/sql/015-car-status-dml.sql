--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:populate-car-status-table splitStatements:true endDelimiter:;
INSERT INTO CAR_RENTAL.CAR_STATUS (STATUS)
VALUES ('READY');
INSERT INTO CAR_RENTAL.CAR_STATUS (STATUS)
VALUES ('BROKEN');
INSERT INTO CAR_RENTAL.CAR_STATUS (STATUS)
VALUES ('REPAIRING');