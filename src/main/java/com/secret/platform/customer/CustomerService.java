package com.secret.platform.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> getCustomerById(Long customerId);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
