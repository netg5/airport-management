package org.sergei.flightservice.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.FlightServiceApplication;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.repository.AircraftRepository;
import org.sergei.flightservice.repository.RouteRepository;
import org.sergei.flightservice.testconfig.ResourceServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link AircraftController}
 *
 * @author Sergei Visotsky
 * @since 12/12/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
public class AircraftControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftControllerTest.class);

    private static final String BASE_URL = "/aircrafts";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    public void getAllAircrafts_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].aircraftId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].model").value(model))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].aircraftName").value(aircraftName))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].maxPassengers").value(maxPassengers))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0]._links.self.href").value("http://localhost/aircrafts/1"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/aircrafts"));
    }

    @Test
    public void getAircraftById_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        mvc.perform(
                get(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(maxPassengers))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/aircrafts/1"))
                .andExpect(jsonPath("$._links.allAircrafts.href").value("http://localhost/aircrafts"));
    }

    @Test
    public void postAircraft_thenGetCreated() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;

        JSONObject jsonObject = new JSONObject()
                .put("model", model)
                .put("aircraftName", aircraftName)
                .put("aircraftWeight", aircraftWeight)
                .put("maxPassengers", maxPassengers);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(maxPassengers));
    }

    @Test
    public void postAircraft_thenPutAircraft_thenGetOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;

        JSONObject jsonObject = new JSONObject()
                .put("model", model)
                .put("aircraftName", aircraftName)
                .put("aircraftWeight", aircraftWeight)
                .put("maxPassengers", maxPassengers);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(maxPassengers));

        final String putModel = "747-400";
        final String putAircraft = "Boeing";
        final Double putAircraftWeight = 30000.0;
        final Integer putMaxPassengers = 2300;
        JSONObject putJsonObject = new JSONObject()
                .put("model", putModel)
                .put("aircraftName", putAircraft)
                .put("aircraftWeight", putAircraftWeight)
                .put("maxPassengers", putMaxPassengers);

        mvc.perform(
                put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(putJsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(putModel))
                .andExpect(jsonPath("$.aircraftName").value(putAircraft))
                .andExpect(jsonPath("$.aircraftWeight").value(putAircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(putMaxPassengers));
    }

    @Test
    public void postAircraft_thenPatchAircraft_thenGetOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;

        JSONObject jsonObject = new JSONObject()
                .put("model", model)
                .put("aircraftName", aircraftName)
                .put("aircraftWeight", aircraftWeight)
                .put("maxPassengers", maxPassengers);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(maxPassengers));

        final String putModel = "747-400";
        final String putAircraft = "Boeing";
        final Double putAircraftWeight = 30000.0;
        final Integer putMaxPassengers = 2300;
        JSONObject putJsonObject = new JSONObject()
                .put("model", putModel)
                .put("aircraftName", putAircraft)
                .put("aircraftWeight", putAircraftWeight)
                .put("maxPassengers", putMaxPassengers);

        mvc.perform(
                patch(BASE_URL + "/1/patch")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(putJsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(putModel))
                .andExpect(jsonPath("$.aircraftName").value(putAircraft))
                .andExpect(jsonPath("$.aircraftWeight").value(putAircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(putMaxPassengers));
    }

    @Ignore
    @Test
    public void postAircraft_thenDelete_thenGetNoContent() throws Exception {
        final long aircraftId = 1L;
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;

        JSONObject jsonObject = new JSONObject()
                .put("aircraftId", aircraftId)
                .put("model", model)
                .put("aircraftName", aircraftName)
                .put("aircraftWeight", aircraftWeight)
                .put("maxPassengers", maxPassengers);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(maxPassengers));
        LOGGER.info("Aircraft ID: {}", aircraftId);

        mvc.perform(delete(BASE_URL + "/" + aircraftId))
                .andExpect(status().isNoContent());
    }

    private Aircraft setupAircraft(String model, String aircraftName,
                                   Double aircraftWeight, Integer maxPassengers) {
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(model);
        aircraft.setAircraftName(aircraftName);
        aircraft.setAircraftWeight(aircraftWeight);
        aircraft.setMaxPassengers(maxPassengers);
        return aircraftRepository.save(aircraft);
    }
}
