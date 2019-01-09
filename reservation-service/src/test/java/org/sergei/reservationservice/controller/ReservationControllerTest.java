package org.sergei.reservationservice.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservationservice.ReservationServiceApplication;
import org.sergei.reservationservice.model.Aircraft;
import org.sergei.reservationservice.model.Customer;
import org.sergei.reservationservice.model.Reservation;
import org.sergei.reservationservice.model.Route;
import org.sergei.reservationservice.repository.AircraftRepository;
import org.sergei.reservationservice.repository.CustomerRepository;
import org.sergei.reservationservice.repository.RouteRepository;
import org.sergei.reservationservice.testconfig.ResourceServerConfiguration;
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
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link ReservationController}
 *
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.reservationservice.repository")
@EntityScan(basePackages = "org.sergei.reservationservice.model")
public class ReservationControllerTest {

    private static final String BASE_URL = "https://localhost:80/customers";
    private static final String RESERVATIONS_PATH = "/reservations";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    public void getAllReservations_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
        Route route = new Route(distance, departureTime, arrivalTime, price, place, aircraft, Collections.emptyList());

        final LocalDateTime reservationDate = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        setupReservation(reservationDate, customer, route);

        mvc.perform(
                get(BASE_URL + "/" + customer.getCustomerId() + RESERVATIONS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservationId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservationDate").value(reservationDate))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0]._links.reservation.href", is(BASE_URL + "/2" + RESERVATIONS_PATH + "/1")))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.routeId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.distance").value(distance))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.departureTime").value(departureTime))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place").value(place))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.aircraft").value(place))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.model").value(model))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].allReservedRoutes.place.aircraft.maxPassengers").value(maxPassengers))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL + "/1" + RESERVATIONS_PATH)));
    }

    @Ignore
    @Test
    public void getOneReservation_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
        Route route = new Route(distance, departureTime, arrivalTime, price, place, aircraft, Collections.emptyList());

        final LocalDateTime reservationDate = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        Reservation reservation = setupReservation(reservationDate, customer, route);

        mvc.perform(
                get(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").isNotEmpty())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$.reservationDate").value(reservationDate))
                .andExpect(jsonPath("$._links.customer.href", is(BASE_URL + "/2")))
                .andExpect(jsonPath("$._links.reservation.href", is(BASE_URL + "/2/" + RESERVATIONS_PATH + "/1")))
                .andExpect(jsonPath("$.allReservedRoutes.routeId").isNotEmpty())
                .andExpect(jsonPath("$.allReservedRoutes.distance").value(distance))
                .andExpect(jsonPath("$.allReservedRoutes.departureTime").value(departureTime))
                .andExpect(jsonPath("$.allReservedRoutes.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$.allReservedRoutes.place").value(place))
                .andExpect(jsonPath("$.allReservedRoutes.aircraft").value(place))
                .andExpect(jsonPath("$.allReservedRoutes.place.aircraft.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.allReservedRoutes.place.aircraft.model").value(model))
                .andExpect(jsonPath("$.allReservedRoutes.place.aircraft.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.allReservedRoutes.place.aircraft.aircraftWeight").value(aircraftWeight))
                .andExpect(jsonPath("$.allReservedRoutes.place.aircraft.maxPassengers").value(maxPassengers));
    }

    @Test
    public void postReservation_thenReturnCreated() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        final String reservationDate = "2018-09-28T22:00:00";

        JSONObject jsonObject = new JSONObject()
                .put("routeId", route.getRouteId())
                .put("reservationDate", reservationDate);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("customerId").value(1))
                .andExpect(jsonPath("routeId").value(route.getRouteId()))
                .andExpect(jsonPath("reservationDate").value(reservationDate));
    }

    @Test
    public void postReservation_thenPatch_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        final String reservationDate = "2018-09-28T22:00:00";

        JSONObject jsonObject = new JSONObject()
                .put("routeId", route.getRouteId())
                .put("reservationDate", reservationDate);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("routeId").value(route.getRouteId()))
                .andExpect(jsonPath("reservationDate").value(reservationDate));

        final String reservationDateAfter = "2019-01-01T15:27:05";
        JSONObject jsonObjectAfter = new JSONObject()
                .put("routeId", route.getRouteId())
                .put("reservationDate", reservationDateAfter);
        mvc.perform(
                patch(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObjectAfter.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("routeId").value(route.getRouteId()))
                .andExpect(jsonPath("reservationDate").value(reservationDateAfter));
    }

    @Test
    public void postReservation_thenDelete_thenReturnNoContent() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        final String reservationDate = "2018-09-28T22:00:00";

        JSONObject jsonObject = new JSONObject()
                .put("routeId", route.getRouteId())
                .put("reservationDate", reservationDate);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("customerId").value(1))
                .andExpect(jsonPath("routeId").value(route.getRouteId()))
                .andExpect(jsonPath("reservationDate").value(reservationDate));

        mvc.perform(delete(BASE_URL + "/1")).andExpect(status().isNoContent());
    }

    private Reservation setupReservation(LocalDateTime reservationDate, Customer customer, Route route) {
        Reservation reservation = new Reservation();

        reservation.setReservationDate(reservationDate);
        reservation.setCustomer(customer);
        reservation.setRoute(route);

        return reservation;
    }

    private Customer setupCustomer(String firstName, String lastName, int age) {
        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);
        customer.setReservations(Collections.emptyList());

        return customerRepository.save(customer);
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
