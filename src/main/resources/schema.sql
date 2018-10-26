DROP DATABASE IF EXISTS flight_reservation_oauth;
CREATE DATABASE flight_reservation_oauth /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

DROP TABLE IF EXISTS flight_reservation_oauth.oauth_client_details;
CREATE TABLE flight_reservation_oauth.oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);

DROP TABLE IF EXISTS flight_reservation_oauth.oauth_client_token;
CREATE TABLE flight_reservation_oauth.oauth_client_token (
  token_id          VARCHAR(256),
  token             BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);

DROP TABLE IF EXISTS flight_reservation_oauth.oauth_access_token;
CREATE TABLE flight_reservation_oauth.oauth_access_token (
  token_id          VARCHAR(256),
  token             BLOB,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BLOB,
  refresh_token     VARCHAR(256)
);

DROP TABLE IF EXISTS rest_services_oauth.oauth_refresh_token;
CREATE TABLE flight_reservation_oauth.oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BLOB,
  authentication BLOB
);

DROP TABLE IF EXISTS rest_services_oauth.oauth_code;
CREATE TABLE flight_reservation_oauth.oauth_code (
  code           VARCHAR(256),
  authentication BLOB
);

DROP TABLE IF EXISTS rest_services_oauth.oauth_approvals;
CREATE TABLE flight_reservation_oauth.oauth_approvals (
  userId         VARCHAR(256),
  clientId       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

DROP TABLE IF EXISTS rest_services_oauth.client_details;
CREATE TABLE flight_reservation_oauth.client_details (
  appId                  VARCHAR(256) PRIMARY KEY,
  resourceIds            VARCHAR(256),
  appSecret              VARCHAR(256),
  scope                  VARCHAR(256),
  grantTypes             VARCHAR(256),
  redirectUrl            VARCHAR(256),
  authorities            VARCHAR(256),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(4096),
  autoApproveScopes      VARCHAR(256)
);