DROP TABLE IF EXISTS PERSONS CASCADE;
CREATE TABLE PERSONS
(
    ID        serial primary key,
    FIRST_NAME VARCHAR,
    LAST_NAME  VARCHAR,
    OIB        VARCHAR,
    STATUS     VARCHAR
);