package org.sergei.reservationservice.model;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservationservice.testconfig.WebSecurityConfigTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for {@link Customer}
 *
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EntityScan(basePackages = "org.sergei.reservationservice.model")
public class CustomerTest {

    @Test
    public void typeAssertion() {
    }
}