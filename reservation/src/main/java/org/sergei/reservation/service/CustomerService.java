package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.CustomerDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface CustomerService {
    CustomerDTO findOne(Long customerId);

    List<CustomerDTO> findAll();

    List<String> findIdsOfAllCustomers();

    Page<CustomerDTO> findAllPaginated(int page, int size);

    CustomerDTO save(CustomerDTO customerDTO);

    CustomerDTO update(Long customerId, CustomerDTO customerDTO);

    CustomerDTO patch(Long customerId, Map<String, Object> params);

    CustomerDTO delete(Long customerId);
}
