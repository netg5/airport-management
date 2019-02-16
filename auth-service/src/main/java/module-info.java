/**
 * @author Sergei Visotsky
 */
open module auth.service {
    requires spring.boot.starter.security;
    requires spring.cloud.starter.oauth2;
    requires spring.cloud.starter.security;
    requires spring.security.jwt;
    requires spring.boot.starter.data.jpa;
    requires spring.cloud.starter.netflix.eureka.client;
    requires spring.boot.starter.aop;
    requires springfox.swagger2;
    requires springfox.swagger.ui;
}