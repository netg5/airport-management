import org.sergei.flightservice.controller.CustomerController
import org.sergei.flightservice.model.Customer
import org.sergei.flightservice.repository.CustomerRepository
import org.sergei.flightservice.test.config.WebSecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Ignore
@WebMvcTest(controllers = CustomerController)
@ContextConfiguration(classes = WebSecurityConfig.class)
class CustomerControllerTestGroovy extends Specification {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    def "should create customer save it into the in-memory database and call for the response to REST controller"() {
        given:
        Map request = [
                customerId = 1,
                firstName = 'John',
                lastName = 'Smith',
                age = 25
        ]

        and:
        customerRepository.save() >> new Customer("John", "Smith", 25, Collections.emptyList())

        when:
        def results = mvc.perform(get('/v1/customers')
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
                .andReturn().response

        then:
        results.andExpect(status().isOk())

        and:
        results.andExpect(jsonPath('$.customerId').value(1))
        results.andExpect(jsonPath('$.firstName').value('John'))
        results.andExpect(jsonPath('$.lastName').value('Smith'))
        results.andExpect(jsonPath('$.age').value(25))
    }

}
