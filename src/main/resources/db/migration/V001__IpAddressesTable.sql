--DROP TABLE IF EXISTS ip_addresses;

CREATE TABLE IF NOT EXISTS ip_addresses

/*
----------------------------------------------------------------------------------------------------
* Table ip_addresses contains ip addresses and other details of users who have displayed Bitcoin Price Checker
----------------------------------------------------------------------------------------------------
* 2024-02-03 - zluzar - initial table creation
----------------------------------------------------------------------------------------------------
*/
(
    id                      BIGSERIAL,
    ip_address              VARCHAR(100) NOT NULL,
    owner                   VARCHAR(100),
    currency                VARCHAR(100) NOT NULL,
    night_mode              BOOLEAN      NOT NULL,
    last_page_refresh       TIMESTAMP    NOT NULL,
    CONSTRAINT ip_addresses_pk PRIMARY KEY (id)
);
