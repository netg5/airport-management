package org.sergei.reservation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.HangarDTO;
import org.sergei.reservation.rest.dto.ManufacturerDTO;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.response.RouteDTOExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void routeRestCallJacksonReadValueTest() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(managerRouteUri + "/1", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        RouteDTOExchangeResponse routeDTOExchangeResponse = objectMapper.readValue(responseEntity.getBody(), RouteDTOExchangeResponse.class);
        log.info("JsonNode as String: {}", routeDTOExchangeResponse.getResponse().get(0).toString());

    }

    @Test
    public void routeRestCallTest() throws IOException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(managerRouteUri + "/1", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

        String responseStr = jsonNode.at("/response").toString();
        JSONArray jsonArray = new JSONArray(responseStr);

        log.info("JSON array as string: {}", jsonArray);

        Map<String, LinkedHashMap<String, Object>> map = new LinkedHashMap<>();
        map = objectMapper.convertValue(responseStr, map.getClass());

        for (Map.Entry<String, LinkedHashMap<String, Object>> entry : map.entrySet()) {
            RouteDTO routeDTO = RouteDTO.builder()
                    .routeId((Long) entry.getValue().get("routeId"))
                    .departureTime(LocalDateTime.parse((CharSequence) entry.getValue().get("departureTime")))
                    .arrivalTime(LocalDateTime.parse((CharSequence) entry.getValue().get("arrivalTime")))
                    .distance((Double) entry.getValue().get("distance"))
                    .place((String) entry.getValue().get("place"))
                    .price((BigDecimal) entry.getValue().get("price"))
                    .aircraft(AircraftDTO.builder()
                            .aircraftId((Long) entry.getValue().get("aircraftId"))
                            .registrationNumber((String) entry.getValue().get("registrationNumber"))
                            .modelNumber((String) entry.getValue().get("modelNumber"))
                            .aircraftName((String) entry.getValue().get("aircraftName"))
                            .capacity((Integer) entry.getValue().get("capacity"))
                            .weight((Double) entry.getValue().get("weight"))
                            .exploitationPeriod((Integer) entry.getValue().get("exploitationPeriod"))
                            .hangar(HangarDTO.builder()
                                    .id((Long) entry.getValue().get("hangarId"))
                                    .capacity((Integer) entry.getValue().get("capacity"))
                                    .hangarLocation((String) entry.getValue().get("location"))
                                    .hangarNumber((String) entry.getValue().get("hangarNumber"))
                                    .build())
                            .manufacturer(ManufacturerDTO.builder()
                                    .id((Long) entry.getValue().get("manufacturerId"))
                                    .location((String) entry.getValue().get("location"))
                                    .manufacturerCode((String) entry.getValue().get("manufacturerCode"))
                                    .manufacturerName((String) entry.getValue().get("manufacturerName"))
                                    .build())
                            .build())
                    .build();
            log.info("Route info is: {}", routeDTO.getDistance());
        }
//        RouteDTO routeDTO = RouteDTO.builder()
//                .routeId(jsonNode.path("routeId").asLong())
//                .departureTime(LocalDateTime.parse(jsonNode.path("departureTime").asText()))
//                .arrivalTime(LocalDateTime.parse(jsonNode.path("arrivalTime").asText()))
//                .distance(jsonNode.path("distance").asDouble())
//                .place(jsonNode.path("place").asText())
//                .price(jsonNode.path("price").decimalValue())
//                .aircraft(AircraftDTO.builder()
//                        .aircraftId(Long.valueOf(jsonNode.at("/aircraft/aircraftId").toString()))
//                        .registrationNumber(jsonNode.at("/aircraft/registrationNumber").asText())
//                        .modelNumber(jsonNode.at("/aircraft/modelNumber").asText())
//                        .aircraftName(jsonNode.at("/aircraft/aircraftName").asText())
//                        .capacity(jsonNode.at("/aircraft/capacity").asInt())
//                        .weight(jsonNode.at("/aircraft/weight").asDouble())
//                        .exploitationPeriod(jsonNode.at("/aircraft/exploitationPeriod").asInt())
//                        .hangar(HangarDTO.builder()
//                                .id(jsonNode.at("/aircraft/hangar/hangarId").asLong())
//                                .capacity(jsonNode.at("/aircraft/hangar/capacity").asInt())
//                                .hangarLocation(jsonNode.at("/aircraft/hangar/location").asText())
//                                .hangarNumber(jsonNode.at("/aircraft/hangar/hangarNumber").asText())
//                                .build())
//                        .manufacturer(ManufacturerDTO.builder()
//                                .id(jsonNode.at("/aircraft/manufacturer/manufacturerId").asLong())
//                                .location(jsonNode.at("/aircraft/manufacturer/location").asText())
//                                .manufacturerCode(jsonNode.at("/aircraft/manufacturer/manufacturerCode").asText())
//                                .manufacturerName(jsonNode.at("/aircraft/manufacturer/manufacturerName").asText())
//                                .build())
//                        .build())
//                .build();
    }
}
