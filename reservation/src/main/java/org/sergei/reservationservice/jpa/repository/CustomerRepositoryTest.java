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

package org.sergei.reservationservice.jpa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservationservice.jpa.model.Customer;
import org.sergei.reservationservice.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link CustomerRepository}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.reservationservice.jpa.repository")
@EntityScan(basePackages = "org.sergei.reservationservice.jpa.model")
public class CustomerRepositoryTest {

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Customer> customerList = customerRepository.findAll();
        assertTrue(customerList.isEmpty());
    }

    @Test
    public void saveCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        customerRepository.save(customer);
        Iterable<Customer> foundAll = customerRepository.findAll();
        assertThat(foundAll).hasSize(1);
        customer.setCustomerId(1L);
        assertThat(foundAll).contains(customer);
    }

    @Test
    public void saveCustomer_deleteCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        customerRepository.save(customer);
        Iterable<Customer> foundAll = customerRepository.findAll();
        assertThat(foundAll).hasSize(1);
        customer.setCustomerId(1L);
        assertThat(foundAll).contains(customer);
        customerRepository.delete(customer);
        Iterable<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(0);
    }
}
