package org.sergei.flightservice.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.test.config.ApplicationJpaConfig;
import org.sergei.flightservice.test.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Sergei Visotsky, 2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {ApplicationJpaConfig.class, WebSecurityConfig.class})
@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
@EntityScan(basePackages = "org.sergei.flightservice.model")
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Customer> customerList = customerRepository.findAll();
        Assert.assertTrue(customerList.isEmpty());
    }

    @Test
    public void saveCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 20, Collections.emptyList());
        customerRepository.save(customer);
        Customer foundCustomer = customerRepository.findById(1L).orElse(null);
        Assert.assertEquals(Objects.requireNonNull(foundCustomer).getFirstName(), customer.getFirstName());
    }
}
