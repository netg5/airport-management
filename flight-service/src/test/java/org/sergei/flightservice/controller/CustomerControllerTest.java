package org.sergei.flightservice.controller;

import org.json.JSONObject;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomers_thenReturnOk() throws Exception {
        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void getAllCustomers() throws Exception {
        final Long customerId = 1L;
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        setupCustomer(customerId, firstName, lastName, age);
        final Long customerTwoId = 2L;
        final String firstNameTwo = "JohnTwo";
        final String lastNameTwo = "SmithTwo";
        final int ageTwo = 21;
        setupCustomer(customerTwoId, firstNameTwo, lastNameTwo, ageTwo);

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customerDTOList[0].customerId").value(1L))
                .andExpect(jsonPath("$._embedded.customerDTOList[0].firstName").value("John"))
                .andExpect(jsonPath("$._embedded.customerDTOList[0].lastName").value("Smith"))
                .andExpect(jsonPath("$._embedded.customerDTOList[0].age").value(20))
                .andExpect(jsonPath("$._embedded.customerDTOList[0]._links.self.href").value("http://localhost/customers/" + customerId))
                .andExpect(jsonPath("$._embedded.customerDTOList[0]._links.reservations.href").value("http://localhost/customers/" + customerId + "/reservations"))
                .andExpect(jsonPath("$._embedded.customerDTOList[1].customerId").value(2L))
                .andExpect(jsonPath("$._embedded.customerDTOList[1].firstName").value("JohnTwo"))
                .andExpect(jsonPath("$._embedded.customerDTOList[1].lastName").value("SmithTwo"))
                .andExpect(jsonPath("$._embedded.customerDTOList[1].age").value(21))
                .andExpect(jsonPath("$._embedded.customerDTOList[1]._links.self.href").value("http://localhost/customers/" + customerTwoId))
                .andExpect(jsonPath("$._embedded.customerDTOList[1]._links.reservations.href").value("http://localhost/customers/" + customerTwoId + "/reservations"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/customers"));
    }

    @Test
    public void getCustomerById_thenGetOk() throws Exception {
        final Long customerId = 1L;
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        setupCustomer(customerId, firstName, lastName, age);

        mvc.perform(
                get(BASE_URL + "/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/customers/" + customerId))
                .andExpect(jsonPath("$._links.reservations.href").value("http://localhost/customers/" + customerId + "/reservations"))
                .andExpect(jsonPath("$._links.allCustomers.href").value("http://localhost/customers"));
    }

    @Test
    public void postCustomer_thenGetCreated() throws Exception {
        final long customerId = 1L;
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

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

    @Test
    public void postCustomer_thenPutCustomer_thenGetOk() throws Exception {
        final long customerId = 1L;
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

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
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age));

        final String putFirstName = "JohnP";
        final String putLastName = "SmithP";
        final int putAge = 21;

        JSONObject putJsonObject = new JSONObject()
                .put("customerId", customerId)
                .put("firstName", putFirstName)
                .put("lastName", putLastName)
                .put("age", putAge);
        mvc.perform(
                put(BASE_URL + "/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(putJsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.firstName").value(putFirstName))
                .andExpect(jsonPath("$.lastName").value(putLastName))
                .andExpect(jsonPath("$.age").value(putAge));
    }

    @Test
    public void postCustomer_thenDelete_thenGetNoContent() throws Exception {
        final long customerId = 1L;
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

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
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age));

        mvc.perform(
                delete(BASE_URL + "/" + customerId))
                .andExpect(status().isNoContent());
    }

    private Customer setupCustomer(Long customerId, String firstName, String lastName, int age) {
        Customer customer = new Customer();

        customer.setCustomerId(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);
        customer.setReservations(Collections.emptyList());

        return customerRepository.save(customer);
    }
}
