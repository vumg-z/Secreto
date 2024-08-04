package com.secret.platform.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toDTO(savedCustomer);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).map(CustomerMapper::toDTO);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerMapper::toDTO).toList();
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getId() == null || !customerRepository.existsById(customerDTO.getId())) {
            throw new IllegalArgumentException("Customer with ID " + customerDTO.getId() + " does not exist");
        }
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerMapper.toDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist");
        }
        customerRepository.deleteById(customerId);
    }

    public Optional<CustomerDTO> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).map(CustomerMapper::toDTO);
    }

    public List<CustomerDTO> findCustomersByFirstName(String firstName) {
        return customerRepository.findByFirstName(firstName).stream().map(CustomerMapper::toDTO).toList();
    }

    public List<CustomerDTO> findCustomersByLastName(String lastName) {
        return customerRepository.findByLastName(lastName).stream().map(CustomerMapper::toDTO).toList();
    }

    public List<CustomerDTO> findCustomersByFirstNameAndLastName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName).stream().map(CustomerMapper::toDTO).toList();
    }
}
