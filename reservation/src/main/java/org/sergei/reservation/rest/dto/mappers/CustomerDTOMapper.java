package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Customer;
import org.sergei.reservation.rest.dto.CustomerResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CustomerDTOMapper implements IMapper<Customer, CustomerResponseDTO> {

    @Override
    public CustomerResponseDTO apply(Customer customer) {

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();

        customerResponseDTO.setCustomerId(customer.getId());
        customerResponseDTO.setFirstName(customer.getFirstName());
        customerResponseDTO.setLastName(customer.getLastName());
        customerResponseDTO.setAge(customer.getAge());

        return customerResponseDTO;
    }
}
