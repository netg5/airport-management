package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Customer;
import org.sergei.reservation.rest.dto.CustomerDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CustomerDTOMapper implements IMapper<Customer, CustomerDTO> {

    @Override
    public CustomerDTO apply(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAge(customer.getAge());

        return customerDTO;
    }
}
