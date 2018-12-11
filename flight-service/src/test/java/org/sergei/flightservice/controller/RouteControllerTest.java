package org.sergei.flightservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.FlightServiceApplication;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.model.Route;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.repository.RouteRepository;
import org.sergei.flightservice.testconfig.AppConfigTest;
import org.sergei.flightservice.testconfig.ResourceServerConfiguration;
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

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link RouteController}
 *
 * @author Sergei Visotsky
 * @since 12/11/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {AppConfigTest.class, ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
public class RouteControllerTest {
    private static final String BASE_URL = "/routes";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RouteRepository routeRepository;

    @Test
    public void getAllRoutes_thenReturnOk() throws Exception {
        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
    }

    private Route setupRoute(Long customerId, String firstName, String lastName, int age) {
        Route route = new Route();



        return routeRepository.save(route);
    }
}
