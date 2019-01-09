package org.sergei.reservationservice.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservationservice.ReservationServiceApplication;
import org.sergei.reservationservice.model.Aircraft;
import org.sergei.reservationservice.repository.AircraftRepository;
import org.sergei.reservationservice.testconfig.ResourceServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link AircraftController}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
@ContextConfiguration(classes = {ResourceServerConfiguration.class}, initializers = ConfigFileApplicationContextInitializer.class)
@EnableJpaRepositories(basePackages = "org.sergei.reservationservice.repository")
@EntityScan(basePackages = "org.sergei.reservationservice.model")
public class AircraftControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftControllerTest.class);

    private static final String BASE_URL = "https://localhost:80/aircrafts";

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
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0]._links.self.href", is(BASE_URL + "/1")))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL)));
        aircraftRepository.deleteAll();
    }

    @Test
    public void getAllAircraftsPaginated_thenReturnOk() throws Exception {
        final String page = "?page=1";
        final String size = "&size=1";

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final String secondModel = "340-750";
        final String secondAircraftName = "SecondTestAircraft";
        final Double secondAircraftWeight = 1000.0;
        final Integer secondMaxPassengers = 1000;
        Aircraft secondAircraft = setupAircraft(secondModel, secondAircraftName, secondAircraftWeight, secondMaxPassengers);

        mvc.perform(
                get(BASE_URL + page + size)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].aircraftId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].model").value(secondModel))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].aircraftName").value(secondAircraftName))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].aircraftWeight").value(secondAircraftWeight))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0].maxPassengers").value(secondMaxPassengers))
                .andExpect(jsonPath("$._embedded.aircraftDTOList[0]._links.self.href", is(BASE_URL + "/" + secondAircraft.getAircraftId())))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL + page + size)));
        aircraftRepository.deleteAll();
    }

    @Test
    public void getAircraftById_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        mvc.perform(
                get(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$.maxPassengers").value(maxPassengers))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL + "/" + aircraft.getAircraftId())))
                .andExpect(jsonPath("$._links.allAircrafts.href", is(BASE_URL)));
        aircraftRepository.deleteAll();
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
        aircraftRepository.deleteAll();
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
        aircraftRepository.deleteAll();
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
        aircraftRepository.deleteAll();
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
        aircraftRepository.deleteAll();
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
