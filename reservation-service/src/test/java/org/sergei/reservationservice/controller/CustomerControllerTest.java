package org.sergei.reservationservice.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservationservice.ReservationServiceApplication;
import org.sergei.reservationservice.model.Customer;
import org.sergei.reservationservice.repository.CustomerRepository;
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

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CustomerController}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.reservationservice.repository")
@EntityScan(basePackages = "org.sergei.reservationservice.model")
public class CustomerControllerTest {

    private static final String BASE_URL = "https://localhost:80/customers";
    private static final String RESERVATIONS_PATH = "/reservations";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomers_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        setupCustomer(firstName, lastName, age);

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customerDTOList[0].customerId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.customerDTOList[0].firstName").value(firstName))
                .andExpect(jsonPath("$._embedded.customerDTOList[0].lastName").value(lastName))
                .andExpect(jsonPath("$._embedded.customerDTOList[0].age").value(age))
                .andExpect(jsonPath("$._embedded.customerDTOList[0]._links.self.href", is(BASE_URL + "/2")))
                .andExpect(jsonPath("$._embedded.customerDTOList[0]._links.reservations.href", is(BASE_URL + "/2" + RESERVATIONS_PATH)))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL)));
    }

    @Test
    public void getIdsOfCustomers_thenReturnOk() throws Exception {
        final String firstNameOne = "John";
        final String lastNameOne = "Smith";
        final int ageOne = 20;
        Customer firstCustomer = setupCustomer(firstNameOne, lastNameOne, ageOne);

        final String firstNameTwo = "Jerry Name";
        final String lastNameTwo = "Jerry";
        final int ageTwo = 45;
        Customer secondCustomer = setupCustomer(firstNameTwo, lastNameTwo, ageTwo);
        mvc.perform(
                get(BASE_URL + "/ids")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customerIdsDTOList[0].customerId").isNotEmpty())
                .andExpect(jsonPath("$._embedded.customerIdsDTOList[1].customerId").isNotEmpty())
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL + "/ids")))
                .andExpect(jsonPath("$._links.allCustomers.href", is(BASE_URL)));
    }

    @Test
    public void getCustomerById_thenGetOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        setupCustomer(firstName, lastName, age);

        mvc.perform(
                get(BASE_URL + "/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$._links.self.href", is(BASE_URL + "/2")))
                .andExpect(jsonPath("$._links.reservations.href", is(BASE_URL + "/2" + RESERVATIONS_PATH)))
                .andExpect(jsonPath("$._links.allCustomers.href", is(BASE_URL)));
    }

    @Test
    public void postCustomer_thenGetCreated() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        JSONObject jsonObject = new JSONObject()
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
    }

    @Test
    public void postCustomer_thenPutCustomer_thenGetOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        JSONObject jsonObject = new JSONObject()
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

        final String putFirstName = "JohnP";
        final String putLastName = "SmithP";
        final int putAge = 21;

        JSONObject putJsonObject = new JSONObject()
                .put("firstName", putFirstName)
                .put("lastName", putLastName)
                .put("age", putAge);
        mvc.perform(
                put(BASE_URL + "/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(putJsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(putFirstName))
                .andExpect(jsonPath("$.lastName").value(putLastName))
                .andExpect(jsonPath("$.age").value(putAge));
    }

    @Test
    public void postCustomer_thenPatchCustomer_thenGetOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        JSONObject jsonObject = new JSONObject()
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

        final String patchFirstName = "JohnP";
        final String patchLastName = "SmithP";
        final int patchAge = 21;

        JSONObject putJsonObject = new JSONObject()
                .put("firstName", patchFirstName)
                .put("lastName", patchLastName)
                .put("age", patchAge);
        mvc.perform(
                patch(BASE_URL + "/1/patch")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(putJsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(patchFirstName))
                .andExpect(jsonPath("$.lastName").value(patchLastName))
                .andExpect(jsonPath("$.age").value(patchAge));
    }

    @Test
    public void postCustomer_thenDelete_thenGetNoContent() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        JSONObject jsonObject = new JSONObject()
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

        mvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());
    }

    private Customer setupCustomer(String firstName, String lastName, int age) {
        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);
        customer.setReservations(Collections.emptyList());

        return customerRepository.save(customer);
    }
}