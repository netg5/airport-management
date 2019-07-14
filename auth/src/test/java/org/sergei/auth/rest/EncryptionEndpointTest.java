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

package org.sergei.auth.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.auth.AuthApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
public class EncryptionEndpointTest {

    private static final String RESOURCE_URL = "http://localhost/encryption";
    private static final String PASSWORD_PARAM = "?password=";
    private static final int PSWD_TEST_VALUE = 123456;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getEncryptedPassword_thenAssertEquals() throws Exception {
        mockMvc.perform(
                get(RESOURCE_URL + PASSWORD_PARAM + PSWD_TEST_VALUE))
//                .andExpect(content().string("$2a$10$kvQpP6wubWTbpMGkZywHrecOugazqRX1rLGBaUYrZwx90Ra1QUvji"))
                .andExpect(status().isOk());
    }
}
