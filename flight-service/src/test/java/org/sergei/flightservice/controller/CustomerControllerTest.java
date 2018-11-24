package org.sergei.flightservice.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.dto.CustomerDTO;
import org.sergei.flightservice.service.CustomerService;
import org.sergei.flightservice.test.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sergei Visotsky, 2018
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(properties =
        {
                "spring.cloud.config.enabled=false",
                "spring.cloud.config.discovery.enabled=false"
        }
)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {WebSecurityConfig.class})
//@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
//@EntityScan(basePackages = "org.sergei.flightservice.model")
@ActiveProfiles("test")
public class CustomerControllerTest {

    /*@Autowired
    @Qualifier("customerService")
    private CustomerService customerService;*/

    @Autowired
    private MockMvc mockMvc;

    public CustomerControllerTest() {
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
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8083/flight-api/v1/customers")
                .contentType("application/json")
                .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$..firstName").value(containsInAnyOrder("TestName")))
                .andExpect(jsonPath("$..lastName").value(containsInAnyOrder("Test last name")));
    }
}
