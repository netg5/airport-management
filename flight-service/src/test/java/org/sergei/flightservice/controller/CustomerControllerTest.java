package org.sergei.flightservice.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.test.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CustomerController}
 *
 * @author Sergei Visotsky, 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "spring.cloud.config.discovery.enabled=false"})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {WebSecurityConfig.class})
//@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
//@EntityScan(basePackages = "org.sergei.flightservice.model")
public class CustomerControllerTest {

    /*@Autowired
    @Qualifier("customerService")
    private CustomerService customerService;*/

    @Autowired
    private MockMvc mvc;

    @Test
    public void endpointAccessibilityTest() throws Exception {
        mvc.perform(
                get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void getAllCustomers() throws Exception {
//        CustomerDTO firstCustomer = new CustomerDTO(1L, "TestName", "Test last name", 45);
//        customerService.save(firstCustomer);
        JSONObject jsonObject = new JSONObject()
                .put("customerId", "customerId")
                .put("firstName", "firstName")
                .put("lastName", "lastName")
                .put("age", "age");
        mvc.perform(
                get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$..firstName").value(containsInAnyOrder("TestName")))
                .andExpect(jsonPath("$..lastName").value(containsInAnyOrder("Test last name")));
    }
}
