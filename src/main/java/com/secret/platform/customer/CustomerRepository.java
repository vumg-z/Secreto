package com.secret.platform.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customer by email
    Optional<Customer> findByEmail(String email);

    // Find customers by first name
    List<Customer> findByFirstName(String firstName);

    // Find customers by last name
    List<Customer> findByLastName(String lastName);

    // Find customers by both first and last name
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
