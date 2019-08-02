-- User service schema
CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id               VARCHAR(256),
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256),
    CONSTRAINT oauth_client_details_pk PRIMARY KEY (client_id)
);

CREATE TABLE IF NOT EXISTS auth_user
(
    id       BIGINT       NOT NULL,
    username VARCHAR(45)  NOT NULL,
    password VARCHAR(300) NOT NULL,
    CONSTRAINT auth_user_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS auth_user_roles
(
    id        BIGINT      NOT NULL,
    role_name VARCHAR(45) NOT NULL,
    CONSTRAINT auth_user_role_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS auth_user_auth_user_roles
(
    auth_user_id       BIGINT NOT NULL,
    auth_user_roles_id BIGINT NOT NULL,
    CONSTRAINT auth_user_fk FOREIGN KEY (auth_user_id) REFERENCES auth_user (id),
    CONSTRAINT auth_user_roles_fk FOREIGN KEY (auth_user_roles_id) REFERENCES auth_user_roles (id)
);