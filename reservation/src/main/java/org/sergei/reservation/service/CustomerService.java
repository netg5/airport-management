package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.CustomerDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface CustomerService {
    ResponseEntity<ResponseDTO<CustomerDTO>> findOne(Long customerId);

    ResponseEntity<ResponseDTO<CustomerDTO>> findAll();

    ResponseEntity<ResponseDTO<String>> findIdsOfAllCustomers();

    ResponseEntity<ResponseDTO<CustomerDTO>> findAllPaginated(int page, int size);

    ResponseEntity<ResponseDTO<CustomerDTO>> save(CustomerDTO customerDTO);

    ResponseEntity<ResponseDTO<CustomerDTO>> update(Long customerId, CustomerDTO customerDTO);

    ResponseEntity<ResponseDTO<CustomerDTO>> patch(Long customerId, Map<String, Object> params);

    ResponseEntity<ResponseDTO<CustomerDTO>> delete(Long customerId);
}
