package org.sergei.reservation.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sergei Visotsky
 */
@Ignore
@Slf4j
public class ManagerServiceCallContainerLessTest {

    @Test
    public void getRouteByIdRestCallTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> routeRespDemo = restTemplate.getForEntity("http://localhost:8088/getRouteById/1", String.class);
        log.info("Route response is: {}", routeRespDemo.getBody());
    }

}
