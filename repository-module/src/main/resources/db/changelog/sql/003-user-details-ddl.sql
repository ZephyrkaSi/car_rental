--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:add-user-details-table splitStatements:true endDelimiter:;
CREATE TABLE CAR_RENTAL_LB.USER_DETAILS
(
    ID                 INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME         VARCHAR(30) NOT NULL,
    LAST_NAME          VARCHAR(30) NOT NULL,
    PASSPORT_DATA      VARCHAR(30) NOT NULL,
    TOTAL_RENTAL_TIME  INT         NOT NULL DEFAULT 0,
    DISCOUNT_STATUS_ID INT         NOT NULL DEFAULT 1,
    CONSTRAINT FK_CLIENT_DATA_STATUS FOREIGN KEY (DISCOUNT_STATUS_ID) REFERENCES CAR_RENTAL_LB.DISCOUNT_STATUS (ID)
);

CREATE INDEX IDX_CLIENT_DATA_NAME ON CAR_RENTAL_LB.USER_DETAILS (FIRST_NAME, LAST_NAME);
CREATE INDEX IDX_CLIENT_DATA_STATUS_ID ON CAR_RENTAL_LB.USER_DETAILS (DISCOUNT_STATUS_ID);
--rollback DROP TABLE IF EXISTS CAR_RENTAL_LB.USER_DETAILS