--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:add-car-model-table splitStatements:true endDelimiter:;
CREATE TABLE CAR_RENTAL_LB.CAR_MODEL
(
    ID               INT AUTO_INCREMENT PRIMARY KEY,
    BRAND_NAME       VARCHAR(100) NOT NULL,
    MODEL            VARCHAR(100) NOT NULL,
    BODY_TYPE        VARCHAR(30)  NOT NULL,
    FUEL_TYPE        VARCHAR(30)  NOT NULL,
    ENGINE_VOLUME    FLOAT(10)    NOT NULL,
    TRANSMISSION     VARCHAR(30)  NOT NULL,
    BODY_COLOR       VARCHAR(100) NOT NULL,
    INTERIOR_COLOR   VARCHAR(100) NOT NULL,
    PRICE_PER_MINUTE INT          NOT NULL
);

CREATE INDEX IDX_CAR_1 ON CAR_RENTAL_LB.CAR_MODEL (BRAND_NAME, MODEL, BODY_TYPE, TRANSMISSION);
--rollback DROP TABLE IF EXISTS CAR_RENTAL_LB.CAR_MODEL