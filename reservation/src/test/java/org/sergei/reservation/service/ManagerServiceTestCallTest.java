package org.sergei.reservation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
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
    public void routeRestCallTest() throws IOException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(managerRouteUri + "/1", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        JsonNode aircraftNode = objectMapper.readTree(responseEntity.getBody()).get("aircraft");
        JSONArray jsonArray = new JSONArray(jsonNode.at("/aircraft"));
        RouteDTO routeDTO = RouteDTO.builder()
                .routeId(jsonNode.path("routeId").asLong())
//                .departureTime(LocalDateTime.parse(jsonNode.path("departureTime").asText()))
//                .arrivalTime(LocalDateTime.parse(jsonNode.path("arrivalTime").asText()))
                .distance(jsonNode.path("distance").asDouble())
                .place(jsonNode.path("place").asText())
                .price(jsonNode.path("price").decimalValue())
                .aircraft(AircraftDTO.builder()
                        .aircraftId(Long.valueOf(jsonArray.getJSONObject(0).get("/aircraftId").toString()))
                        .registrationNumber(jsonNode.at("/aircraft/registrationNumber").asText())
                        .modelNumber(jsonNode.at("/aircraft/modelNumber").asText())
                        .aircraftName(jsonNode.at("/aircraft/aircraftName").asText())
                        .capacity(jsonNode.at("/aircraft/capacity").asInt())
                        .weight(jsonNode.at("/aircraft/weight").asDouble())
                        .exploitationPeriod(jsonNode.at("/aircraft/exploitationPeriod").asInt())
                        .hangar(HangarDTO.builder()
                                .id(jsonNode.at("/aircraft/hangar/hangarId").asLong())
                                .capacity(jsonNode.at("/aircraft/hangar/capacity").asInt())
                                .hangarLocation(jsonNode.at("/aircraft/hangar/location").asText())
                                .hangarNumber(jsonNode.at("/aircraft/hangar/hangarNumber").asText())
                                .build())
                        .manufacturer(ManufacturerDTO.builder()
                                .id(jsonNode.at("/aircraft/manufacturer/manufacturerId").asLong())
                                .location(jsonNode.at("/aircraft/manufacturer/location").asText())
                                .manufacturerCode(jsonNode.at("/aircraft/manufacturer/manufacturerCode").asText())
                                .manufacturerName(jsonNode.at("/aircraft/manufacturer/manufacturerName").asText())
                                .build())
                        .build())
                .build();
        log.info("Route info is: {}", routeDTO.getDistance());
    }
}
