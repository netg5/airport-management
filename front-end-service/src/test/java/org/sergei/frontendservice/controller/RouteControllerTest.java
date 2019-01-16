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

package org.sergei.frontendservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.frontendservice.FrontEndServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontEndServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class RouteControllerTest {

    private static final String BASE_URL = "https://localhost/routes/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void routeDataPageTest() throws Exception {
        mockMvc.perform(
                get(BASE_URL + 31))
                .andExpect(status().isOk())
                .andExpect(view().name("route"));
    }
}
