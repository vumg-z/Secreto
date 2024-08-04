package com.secret.platform.customer;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDTO(
                new CustomerDTO.RenterName(customer.getFirstName(), customer.getLastName()),
                new CustomerDTO.Address(customer.getEmail(), customer.getWorkTelephoneNumber(), customer.getCellTelephoneNumber())
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getRenterName().getFirstName());
        customer.setLastName(customerDTO.getRenterName().getLastName());
        customer.setEmail(customerDTO.getAddress().getEmail());
        customer.setWorkTelephoneNumber(customerDTO.getAddress().getWorkTelephoneNumber());
        customer.setCellTelephoneNumber(customerDTO.getAddress().getCellTelephoneNumber());
        return customer;
    }
}
