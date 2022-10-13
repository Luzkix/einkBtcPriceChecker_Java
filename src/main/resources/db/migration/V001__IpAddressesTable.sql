--DROP TABLE IF EXISTS ip_addresses;
CREATE TABLE ip_addresses
(
    id                      BIGSERIAL,
    ip_address              VARCHAR(100) NOT NULL,
    owner                   VARCHAR(100),
    currency                VARCHAR(100) NOT NULL,
    night_mode              BOOLEAN      NOT NULL,
    last_page_refresh       TIMESTAMP    NOT NULL,
    CONSTRAINT ip_addresses_pk PRIMARY KEY (id)
);
