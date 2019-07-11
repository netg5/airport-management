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

package org.sergei.authservice.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.authservice.AuthApplication;
import org.sergei.authservice.jpa.model.AuthUser;
import org.sergei.authservice.jpa.model.AuthUserRoles;
import org.sergei.authservice.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableJpaRepositories(basePackages = "org.sergei.authservice.jpa.repository")
@EntityScan(basePackages = "org.sergei.authservice.jpa.model")
public class AuthUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserFindUser_thenReturnOk() {
        List<AuthUserRoles> authUserRoles = new LinkedList<>();
        authUserRoles.add(new AuthUserRoles("ROLE_ADMIN"));
        AuthUser authUser = new AuthUser("john", "123456", authUserRoles);
        userRepository.save(authUser);
        List<AuthUser> foundAuthUsers = userRepository.findAll();
        assertThat(foundAuthUsers).hasSize(1);
        assertThat(foundAuthUsers).contains(authUser);
    }
}
