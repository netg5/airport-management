package org.sergei.flightservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sergei.flightservice.controller.CustomerController;
import org.sergei.flightservice.dto.CustomerDTO;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author Sergei Visotsky, 2018
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private CustomerDTO mockCustomer = new CustomerDTO(1L, "John", "Smith", 20);

    @Test
    public void getCustomerById() throws Exception {
        Mockito.when(
                customerService.findOne(Mockito.anyLong())
        ).thenReturn(mockCustomer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("v1/customers")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{customerId:1,firstName:John,lastName:Smith,age:20}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }
}