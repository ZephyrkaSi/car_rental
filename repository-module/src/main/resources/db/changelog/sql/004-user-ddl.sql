--liquibase formatted sql
--changeset katsiaryna.silina@gmail.com:add-user-table splitStatements:true endDelimiter:;
CREATE TABLE CAR_RENTAL.USER
(
    ID              INT AUTO_INCREMENT PRIMARY KEY,
    USERNAME        VARCHAR(50)  NOT NULL,
    PASSWORD        VARCHAR(100) NOT NULL,
    EMAIL           VARCHAR(100) NOT NULL,
    ENABLED         TINYINT      NOT NULL DEFAULT 1,
    LASTLOGIN       DATETIME              DEFAULT NOW(),
    ROLE_ID         INT          NOT NULL DEFAULT 1,
    USER_DETAILS_ID INT          NOT NULL,
    CONSTRAINT FK_USER_USER_DETAILS_1 FOREIGN KEY (ROLE_ID) REFERENCES CAR_RENTAL.USER_ROLE (ID),
    CONSTRAINT FK_USER_USER_DETAILS_2 FOREIGN KEY (USER_DETAILS_ID) REFERENCES CAR_RENTAL.USER_DETAILS (ID)
);

CREATE UNIQUE INDEX IDX_UN_USER_EMAIL ON CAR_RENTAL.USER (EMAIL);
CREATE UNIQUE INDEX IDX_UN_USER_USER_NAME ON CAR_RENTAL.USER (USERNAME);
CREATE UNIQUE INDEX IDX_UN_USER_USER_DETAILS_ID ON CAR_RENTAL.USER (USER_DETAILS_ID);
--rollback DROP TABLE IF EXISTS CAR_RENTAL.USER