package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Customer;
import org.sergei.reservation.rest.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class CustomerDTOListMapper implements IMapper<List<Customer>, List<CustomerDTO>> {

    private final CustomerDTOMapper customerDTOMapper;

    @Autowired
    public CustomerDTOListMapper(CustomerDTOMapper customerDTOMapper) {
        this.customerDTOMapper = customerDTOMapper;
    }

    @Override
    public List<CustomerDTO> apply(List<Customer> customers) {
        return customerDTOMapper.applyList(customers);
    }
}
