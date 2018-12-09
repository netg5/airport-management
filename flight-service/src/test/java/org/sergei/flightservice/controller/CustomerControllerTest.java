package org.sergei.flightservice.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.FlightServiceApplication;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.testconfig.AppConfigTest;
import org.sergei.flightservice.testconfig.ResourceServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CustomerController}
 *
 * @author Sergei Visotsky
 * @since 11/24/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {AppConfigTest.class, ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
public class CustomerControllerTest {

    private static final String BASE_URL = "/customers";

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllCustomers_thenReturnOk() throws Exception {
        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void getAllCustomers() throws Exception {
        Long customerId = 1L;
        String firstName = "John";
        String lastName = "Smith";
        int age = 20;

        setupCustomer(customerId, firstName, lastName, age);

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("href", is(BASE_URL)))
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$..firstName").value("John"))
                .andExpect(jsonPath("$..lastName").value("Smith"))
                .andExpect(jsonPath("$..age").value(20));
    }

    @Test
    public void postCustomer_thenGetCreated() throws Exception {
//        customerRepository.save(customer);\
        Long customerId = 1L;
        String firstName = "John";
        String lastName = "Smith";
        int age = 20;

        JSONObject jsonObject = new JSONObject()
                .put("customerId", customerId)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("age", age);

        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.age").value(20));
    }

    private Customer setupCustomer(Long customerId, String firstName, String lastName, int age) {
        Customer customer = new Customer();

        customer.setCustomerId(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);
        customer.setFlightReservations(Collections.emptyList());

        return customer;
    }
}
