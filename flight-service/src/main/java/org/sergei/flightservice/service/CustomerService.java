package org.sergei.flightservice.service;

import org.sergei.flightservice.dto.CustomerDTO;
import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class CustomerService implements IService<CustomerDTO> {

    private static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Find customer by ID
     *
     * @param customerId get customer ID as a parameter
     * @return customer
     */
    @Override
    public CustomerDTO findOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        return ObjectMapperUtils.map(customer, CustomerDTO.class);
    }

    /**
     * Find all customers
     *
     * @return list of customers
     */
    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return ObjectMapperUtils.mapAll(customerList, CustomerDTO.class);
    }

    /**
     * Find all customers with pagination
     *
     * @param page how many pages to show
     * @param size how much entites in one page
     * @return list with entities
     */
    @Override
    public Page<CustomerDTO> findAllPaginated(int page, int size) {
        Page<Customer> customerList = customerRepository.findAll(PageRequest.of(page, size));
        return ObjectMapperUtils.mapAllPages(customerList, CustomerDTO.class);
    }

    /**
     * Save customerDTO
     *
     * @param customerDTO gets customerDTO DTO as a parameter
     * @return customerDTO DTO as a response
     */
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = ObjectMapperUtils.map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return ObjectMapperUtils.map(savedCustomer, CustomerDTO.class);
    }

    /**
     * Method to update customerDTO details
     *
     * @param customerId  gets customer ID as a parameter
     * @param customerDTO gets customerDTO DTO as a parameter
     * @return customerDTO DTO as a response
     */
    @Override
    public CustomerDTO update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());

        customerRepository.save(customer);

        return customerDTO;
    }

    /**
     * Patch specific field of the customer
     *
     * @param customerId customer ID which field should be updated
     * @param params     List of params that should be updated
     * @return patched customer DTO
     */
    @Override
    public CustomerDTO patch(Long customerId, Map<String, Object> params) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        if (params.get("firstName") != null) {
            customer.setFirstName(String.valueOf(params.get("firstName")));
        }
        if (params.get("lastName") != null) {
            customer.setLastName(String.valueOf(params.get("lastName")));
        }
        if (params.get("age") != null) {
            customer.setAge(Integer.valueOf(String.valueOf(params.get("age"))));
        }
        customerRepository.save(customer);

        return ObjectMapperUtils.map(customer, CustomerDTO.class);
    }

    /**
     * Delete customer
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO as a response
     */
    @Override
    public CustomerDTO delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        customerRepository.delete(customer);
        return ObjectMapperUtils.map(customer, CustomerDTO.class);
    }
}
