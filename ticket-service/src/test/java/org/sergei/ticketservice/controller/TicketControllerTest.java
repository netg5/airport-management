/*
 * Copyright 2018-2019 Sergei Visotsky
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

package org.sergei.ticketservice.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.ticketservice.TicketServiceApplication;
import org.sergei.ticketservice.testconfig.ResourceServerConfiguration;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link TicketController}
 *
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.ticketservice.repository")
@EntityScan(basePackages = "org.sergei.ticketservice.model")
//@Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TicketControllerTest {

    private static final String BASE_URL = "https://localhost/tickets";
    private static final String CUSTOMER_ID_PARAM = "?customerId=";

    @Autowired
    private MockMvc mvc;

    @Test
    public void getTickets() throws Exception {
        mvc.perform(
                get(BASE_URL + CUSTOMER_ID_PARAM + 1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ticketList[0].firstName").value("John"))
                .andExpect(jsonPath("$._embedded.ticketList[0].lastName").value("Smith"))
                .andExpect(jsonPath("$._embedded.ticketList[0].age").value(20))
                .andExpect(jsonPath("$._embedded.ticketList[0].routeId").value(1))
                .andExpect(jsonPath("$._embedded.ticketList[0].place").value("Riga"))
                .andExpect(jsonPath("$._embedded.ticketList[0].distance").value(2500))
                .andExpect(jsonPath("$._embedded.ticketList[0].price").value(2500))
                .andExpect(jsonPath("$._embedded.ticketList[0].aircraftName").value("Boeing"))
                .andExpect(jsonPath("$._links.self.href").value(BASE_URL + CUSTOMER_ID_PARAM + 1))
                .andExpect(jsonPath("$._links.customer.href").value("https://127.0.0.1:8080/flight-api/customers/1"));
    }
}
