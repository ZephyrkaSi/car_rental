--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:populate-car-table splitStatements:true endDelimiter:;
INSERT INTO CAR_RENTAL.CAR (STATE_NUMBER, CAR_MODEL_ID, CAR_STATUS_ID)
VALUES ('2911CC-7', 1, 1);
INSERT INTO CAR_RENTAL.CAR (STATE_NUMBER, CAR_MODEL_ID, CAR_STATUS_ID)
VALUES ('2912CC-7', 1, 1);
INSERT INTO CAR_RENTAL.CAR (STATE_NUMBER, CAR_MODEL_ID, CAR_STATUS_ID)
VALUES ('2922CC-7', 2, 1);
INSERT INTO CAR_RENTAL.CAR (STATE_NUMBER, CAR_MODEL_ID, CAR_STATUS_ID)
VALUES ('2930CC-7', 3, 1);
INSERT INTO CAR_RENTAL.CAR (STATE_NUMBER, CAR_MODEL_ID, CAR_STATUS_ID)
VALUES ('4089CC-7', 4, 1);
INSERT INTO CAR_RENTAL.CAR (STATE_NUMBER, CAR_MODEL_ID, CAR_STATUS_ID)
VALUES ('4054CC-7', 5, 1);