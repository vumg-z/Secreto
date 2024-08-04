package com.secret.platform.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        // Convert DTO to entity
        Customer customer = CustomerMapper.toEntity(customerDTO);
        // Save entity to database
        Customer savedCustomer = customerRepository.save(customer);
        // Convert entity back to DTO
        return CustomerMapper.toDTO(savedCustomer);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerMapper::toDTO);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        // Check if the customer exists
        if (customerDTO.getId() == null || !customerRepository.existsById(customerDTO.getId())) {
            throw new IllegalArgumentException("Customer with ID " + customerDTO.getId() + " does not exist");
        }
        // Convert DTO to entity
        Customer customer = CustomerMapper.toEntity(customerDTO);
        // Update customer in database
        Customer updatedCustomer = customerRepository.save(customer);
        // Convert entity back to DTO
        return CustomerMapper.toDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist");
        }
        customerRepository.deleteById(customerId);
    }
}
