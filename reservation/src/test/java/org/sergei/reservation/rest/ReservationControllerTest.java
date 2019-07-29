/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.reservation.rest;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservation.ReservationApplication;
import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.jpa.repository.AircraftRepository;
import org.sergei.reservation.jpa.repository.PassengerRepository;
import org.sergei.reservation.jpa.repository.RouteRepository;
import org.sergei.reservation.rest.controller.ReservationController;
import org.sergei.reservation.testconfig.ResourceServerConfiguration;
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
 * Test for {@link ReservationController}
 *
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.reservation.jpa.repository")
@EntityScan(basePackages = "org.sergei.reservation.jpa.model")
public class ReservationControllerTest {

    private static final String BASE_URL = "http://localhost/customers";
    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String ROUTES_PATH = "/routes";
    private static final String AIRCRAFTS_PATH = "/aircrafts";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    public void getAllReservations_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Passenger passenger = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
//        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
//        Route route = new Route(distance, departureTime, arrivalTime, price, place, aircraft, Collections.emptyList());

        final LocalDateTime reservationDate = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
//        setupReservation(reservationDate, passenger, route);

        mvc.perform(
                get(BASE_URL + "/" + passenger.getId() + RESERVATIONS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservationId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].passengerId").value(passenger.getId()))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].dateOfFlying").value(reservationDate))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0]._links.reservation.href", is(BASE_URL + "/2" + RESERVATIONS_PATH + "/1")))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.routeId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.distance").value(distance))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.departureTime").value(departureTime))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.place").value(place))
//                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute._links.routeSelf.href",
//                        is(BASE_URL + RESERVATIONS_PATH + ROUTES_PATH + "/" + route.getId())))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.aircraftId").value(place))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.place.aircraftId.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.place.aircraftId.model").value(model))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.place.aircraftId.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.place.aircraftId.weight").value(aircraftWeight))
                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].reservedRoute.place.aircraftId.capacity").value(maxPassengers))
//                .andExpect(jsonPath("$._embedded.reservationExtendedDTOList[0].aircraftId._links.aircraftSelf.href",
//                        is(BASE_URL + RESERVATIONS_PATH + AIRCRAFTS_PATH + "/" + aircraft.getId())))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL + "/1" + RESERVATIONS_PATH)));
    }

    @Test
    public void getOneReservation_thenReturnOk() throws Exception {
        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
//        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Passenger passenger = setupCustomer(firstName, lastName, age);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
//        Route route = new Route(distance, departureTime, arrivalTime, price, place, aircraft, Collections.emptyList());

        final LocalDateTime reservationDate = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
//        Reservation reservation = setupReservation(reservationDate, passenger, route);

        mvc.perform(
                get(BASE_URL + "/1" + RESERVATIONS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").isNotEmpty())
                .andExpect(jsonPath("$.passengerId").value(passenger.getId()))
                .andExpect(jsonPath("$.dateOfFlying").value(reservationDate))
                .andExpect(jsonPath("$._links.passenger.href", is(BASE_URL + "/2")))
                .andExpect(jsonPath("$._links.reservation.href", is(BASE_URL + "/2/" + RESERVATIONS_PATH + "/1")))
                .andExpect(jsonPath("$.reservedRoute.routeId").isNotEmpty())
                .andExpect(jsonPath("$.reservedRoute.distance").value(distance))
                .andExpect(jsonPath("$.reservedRoute.departureTime").value(departureTime))
                .andExpect(jsonPath("$.reservedRoute.arrivalTime").value(arrivalTime))
                .andExpect(jsonPath("$.reservedRoute.place").value(place))
//                .andExpect(jsonPath("$.reservedRoute._links.routeSelf.href",
//                        is(BASE_URL + RESERVATIONS_PATH + ROUTES_PATH + "/" + route.getId())))
                .andExpect(jsonPath("$.reservedRoute.aircraftId").value(place))
                .andExpect(jsonPath("$.reservedRoute.place.aircraftId.aircraftId").isNotEmpty())
                .andExpect(jsonPath("$.reservedRoute.place.aircraftId.model").value(model))
                .andExpect(jsonPath("$.reservedRoute.place.aircraftId.aircraftName").value(aircraftName))
                .andExpect(jsonPath("$.reservedRoute.place.aircraftId.weight").value(aircraftWeight))
                .andExpect(jsonPath("$.reservedRoute.place.aircraftId.capacity").value(maxPassengers));
//                .andExpect(jsonPath("$.reservedRoute.aircraftId._links.aircraftSelf.href",
//                        is(BASE_URL + RESERVATIONS_PATH + AIRCRAFTS_PATH + "/" + aircraft.getId())));
    }

    @Test
    public void postReservation_thenReturnCreated() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Passenger passenger = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
//        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
//        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        final String reservationDate = "2018-09-28T22:00:00";

        JSONObject jsonObject = new JSONObject()
//                .put("routeId", route.getId())
                .put("dateOfFlying", reservationDate);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("passengerId").value(1))
//                .andExpect(jsonPath("routeId").value(route.getId()))
                .andExpect(jsonPath("dateOfFlying").value(reservationDate));
    }

    @Test
    public void postReservation_thenPatch_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Passenger passenger = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
//        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
//        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        final String reservationDate = "2018-09-28T22:00:00";

        JSONObject jsonObject = new JSONObject()
//                .put("routeId", route.getId())
                .put("dateOfFlying", reservationDate);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("passengerId").value(passenger.getId()))
//                .andExpect(jsonPath("routeId").value(route.getId()))
                .andExpect(jsonPath("dateOfFlying").value(reservationDate));

        final String reservationDateAfter = "2019-01-01T15:27:05";
        JSONObject jsonObjectAfter = new JSONObject()
//                .put("routeId", route.getId())
                .put("dateOfFlying", reservationDateAfter);
        mvc.perform(
                patch(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObjectAfter.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("passengerId").value(passenger.getId()))
//                .andExpect(jsonPath("routeId").value(route.getId()))
                .andExpect(jsonPath("dateOfFlying").value(reservationDateAfter));
    }

    @Test
    public void postReservation_thenDelete_thenReturnNoContent() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Passenger passenger = setupCustomer(firstName, lastName, age);

        final String model = "747-400";
        final String aircraftName = "Boeing";
        final Double aircraftWeight = 30000.0;
        final Integer maxPassengers = 2300;
//        Aircraft aircraft = new Aircraft(model, aircraftName, aircraftWeight, maxPassengers);

        final Double distance = 3600.0;
        final LocalDateTime departureTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime arrivalTime = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final BigDecimal price = BigDecimal.valueOf(450);
        final String place = "New-York";
//        Route route = setupRoute(distance, departureTime, arrivalTime, price, place, aircraft);

        final String reservationDate = "2018-09-28T22:00:00";

        JSONObject jsonObject = new JSONObject()
//                .put("routeId", route.getId())
                .put("dateOfFlying", reservationDate);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("reservationId").isNotEmpty())
                .andExpect(jsonPath("passengerId").value(1))
//                .andExpect(jsonPath("routeId").value(route.getId()))
                .andExpect(jsonPath("dateOfFlying").value(reservationDate));

        mvc.perform(delete(BASE_URL + "/1")).andExpect(status().isNoContent());
    }

    private Reservation setupReservation(LocalDateTime reservationDate, Passenger passenger, Route route) {
        Reservation reservation = new Reservation();

//        reservation.setReservationDate(reservationDate);
//        reservation.setPassenger(passenger);
//        reservation.setRoute(route);

        return reservation;
    }

    private Passenger setupCustomer(String firstName, String lastName, int age) {
        Passenger passenger = new Passenger();

        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setAge(age);
//        passenger.setReservations(Collections.emptyList());

        return passengerRepository.save(passenger);
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
//        aircraft.setModel(model);
        aircraft.setAircraftName(aircraftName);
        aircraft.setWeight(aircraftWeight);
        aircraft.setCapacity(maxPassengers);
        return aircraftRepository.save(aircraft);
    }
}
