package org.sergei.reservation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.HangarDTO;
import org.sergei.reservation.rest.dto.ManufacturerDTO;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ManagerServiceTestCallTest {

    @Value("${manager.route-uri}")
    private String managerRouteUri;

    @Test
    public void routeRestCallTest() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(managerRouteUri + "/1", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());

        RouteDTO routeDTO = RouteDTO.builder()
                .routeId(rootNode.path("routeId").asLong())
                .departureTime(LocalDateTime.parse(rootNode.path("departureTime").asText()))
                .arrivalTime(LocalDateTime.parse(rootNode.path("arrivalTime").asText()))
                .distance(rootNode.path("distance").asDouble())
                .place(rootNode.path("place").asText())
                .price(rootNode.path("price").decimalValue())
                .aircraft(AircraftDTO.builder()
                        .aircraftId(rootNode.path("aircraftId").asLong())
                        .registrationNumber(rootNode.path("registrationNumber").asText())
                        .modelNumber(rootNode.path("modelNumber").asText())
                        .aircraftName(rootNode.path("aircraftName").asText())
                        .capacity(rootNode.path("capacity").asInt())
                        .weight(rootNode.path("weight").asDouble())
                        .exploitationPeriod(rootNode.path("exploitationPeriod").asInt())
                        .hangar(HangarDTO.builder()
                                .id(rootNode.get("hangarId").asLong())
                                .capacity(rootNode.get("capacity").asInt())
                                .hangarLocation(rootNode.get("location").asText())
                                .hangarNumber(rootNode.get("hangarNumber").asText())
                                .build())
                        .manufacturer(ManufacturerDTO.builder()
                                .id(rootNode.get("manufacturerId").asLong())
                                .location(rootNode.get("location").asText())
                                .manufacturerCode(rootNode.get("manufacturerCode").asText())
                                .manufacturerName(rootNode.get("manufacturerName").asText())
                                .build())
                        .build())
                .build();
        log.info("Route info is: {}", routeDTO);
    }
}
