package org.sergei.flightservice.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.FlightServiceApplication;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.model.Route;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link RouteController}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
public class RouteControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteControllerTest.class);

    private static final String BASE_URL = "http://localhost/routes";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    public void getAllRoutes_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        final BigDecimal price = BigDecimal.valueOf(450.0);
        final String place = "New-York";
        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        LOGGER.info("Departure time variable: {}", departureTime);
        LOGGER.info("Departure time is (object): {}", route.getDepartureTime());

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].routeId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].distance").value(distance))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].departureTime").value("2018-09-28T22:00:00"))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].arrivalTime").value("2018-09-28T22:00:00"))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].price").value(price))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].place").value(place))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0]._links.self.href", is("http://localhost/routes/2")))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].aircraft.aircraftId", is(2)))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].aircraft.model").value(model))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].aircraft.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].aircraft.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$._embedded.routeExtendedDTOList[0].aircraft.maxPassengers").value(maxPassengers))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL)));
    }

    @Test
    public void getRouteById_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        final BigDecimal price = BigDecimal.valueOf(450.0);
        final String place = "New-York";
        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        LOGGER.info("Departure time variable: {}", departureTime);
        LOGGER.info("Departure time is (object): {}", route.getDepartureTime());

        mvc.perform(
                get(BASE_URL + "/" + route.getRouteId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeId").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(distance))
                .andExpect(jsonPath("$.departureTime").value("2018-09-28T22:00:00"))
                .andExpect(jsonPath("$.arrivalTime").value("2018-09-28T22:00:00"))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.place").value(place))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL + "/" + route.getRouteId())))
                .andExpect(jsonPath("$._links.allRoutes.href", is(BASE_URL)))
                .andExpect(jsonPath("$.aircraft.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.aircraft.model", is(model)))
                .andExpect(jsonPath("$.aircraft.aircraftName", is(aircraftName)))
                .andExpect(jsonPath("$.aircraft.aircraftWeight", is(aircraftWeight)))
                .andExpect(jsonPath("$.aircraft.maxPassengers", is(maxPassengers)));
    }

    @Test
    public void postRoute_thenGetCreated() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final String departureTime = "2018-09-28T22:00:00";
        final String arrivalTime = "2018-09-28T22:00:00";
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";

        JSONObject jsonObject = new JSONObject()
                .put("distance", distance)
                .put("departureTime", departureTime)
                .put("arrivalTime", arrivalTime)
                .put("price", price)
                .put("place", place)
                .put("aircraftId", aircraft.getAircraftId());

        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.routeId").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(distance))
                .andExpect(jsonPath("$.departureTime").value(departureTime))
                .andExpect(jsonPath("$.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.place").value(place))
                .andExpect(jsonPath("aircraftId").value(aircraft.getAircraftId()));
    }

    @Test
    public void postRoute_thenPutRoute_thenGetOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final String departureTime = "2018-09-28T22:00:00";
        final String arrivalTime = "2018-09-28T22:00:00";
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";

        JSONObject jsonObject = new JSONObject()
                .put("distance", distance)
                .put("departureTime", departureTime)
                .put("arrivalTime", arrivalTime)
                .put("price", price)
                .put("place", place)
                .put("aircraftId", aircraft.getAircraftId());

        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.routeId").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(distance))
                .andExpect(jsonPath("$.departureTime").value(departureTime))
                .andExpect(jsonPath("$.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.place").value(place))
                .andExpect(jsonPath("aircraftId").value(aircraft.getAircraftId()));

        final Double distanceAfter = 1000.0;
        final String departureTimeAfter = "2019-01-01T19:30:00";
        final String arrivalTimeAfter = "2019-01-01T19:30:00";
        final BigDecimal priceAfter = BigDecimal.valueOf(120);
        final String placeAfter = "Chicago";

        JSONObject jsonObjectAfter = new JSONObject()
                .put("distance", distanceAfter)
                .put("departureTime", departureTimeAfter)
                .put("arrivalTime", arrivalTimeAfter)
                .put("price", priceAfter)
                .put("place", placeAfter)
                .put("aircraftId", aircraft.getAircraftId());

        mvc.perform(
                put(BASE_URL + "/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObjectAfter.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeId").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(distanceAfter))
                .andExpect(jsonPath("$.departureTime").value(departureTimeAfter))
                .andExpect(jsonPath("$.arrivalTime").value(arrivalTimeAfter))
                .andExpect(jsonPath("$.price").value(priceAfter))
                .andExpect(jsonPath("$.place").value(placeAfter))
                .andExpect(jsonPath("aircraftId").value(aircraft.getAircraftId()));
    }

    @Test
    public void postRoute_thenPatch_thenGetOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final String departureTime = "2018-09-28T22:00:00";
        final String arrivalTime = "2018-09-28T22:00:00";
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";

        JSONObject jsonObject = new JSONObject()
                .put("distance", distance)
                .put("departureTime", departureTime)
                .put("arrivalTime", arrivalTime)
                .put("price", price)
                .put("place", place)
                .put("aircraftId", aircraft.getAircraftId());
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.routeId").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(distance))
                .andExpect(jsonPath("$.departureTime").value(departureTime))
                .andExpect(jsonPath("$.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.place").value(place))
                .andExpect(jsonPath("aircraftId").value(aircraft.getAircraftId()));

        final Double distanceAfter = 1205.0;
        final String departureTimeAfter = "2018-01-01T08:15:00";
        final String arrivalTimeAfter = "2018-01-01T08:15:00";
        final BigDecimal priceAfter = BigDecimal.valueOf(230);
        final String placeAfter = "Boston";

        JSONObject jsonObjectAfter = new JSONObject()
                .put("distance", distanceAfter)
                .put("departureTime", departureTimeAfter)
                .put("arrivalTime", arrivalTimeAfter)
                .put("price", priceAfter)
                .put("place", placeAfter)
                .put("aircraftId", aircraft.getAircraftId());

        mvc.perform(
                patch(BASE_URL + "/2/patch")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObjectAfter.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeId").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(distanceAfter))
                .andExpect(jsonPath("$.departureTime").value(departureTimeAfter))
                .andExpect(jsonPath("$.arrivalTime").value(arrivalTimeAfter))
                .andExpect(jsonPath("$.price").value(priceAfter))
                .andExpect(jsonPath("$.place").value(placeAfter))
                .andExpect(jsonPath("aircraftId").value(aircraft.getAircraftId()));
    }

    @Test
    public void postRoute_thenDelete_thenGetNoContent() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = setupAircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final String departureTime = "2018-09-28T22:00:00";
        final String arrivalTime = "2018-09-28T22:00:00";
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";

        JSONObject jsonObject = new JSONObject()
                .put("distance", distance)
                .put("departureTime", departureTime)
                .put("arrivalTime", arrivalTime)
                .put("price", price)
                .put("place", place)
                .put("aircraftId", aircraft.getAircraftId());
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.routeId").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(distance))
                .andExpect(jsonPath("$.departureTime").value(departureTime))
                .andExpect(jsonPath("$.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.place").value(place))
                .andExpect(jsonPath("aircraftId").value(aircraft.getAircraftId()));

        mvc.perform(
                delete(BASE_URL + "/1"))
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

    private Route setupRoute(Double distance, LocalDateTime departureTime,
                             LocalDateTime arrivalTime, BigDecimal price,
                             String place, Aircraft aircraft) {
        Route route = new Route();
        route.setDistance(distance);
        route.setDepartureTime(departureTime);
        route.setArrivalTime(arrivalTime);
        route.setPrice(price);
        route.setPlace(place);
        route.setAircraft(aircraft);
        return routeRepository.save(route);
    }
}
