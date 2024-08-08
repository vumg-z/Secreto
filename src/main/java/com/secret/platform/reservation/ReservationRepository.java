package com.secret.platform.reservation;

import com.secret.platform.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomer(Customer customer);

    Optional<Reservation> findByIdAndStatus(Long id, Reservation.Status status);


    List<Reservation> findByCustomerAndStatus(Customer customer, Reservation.Status status);

    // Add method for fetching all active reservations
    List<Reservation> findAllByStatus(Reservation.Status status);

    Optional<Reservation> findByConfirmationNumberAndCustomerLastName(String confirmationNumber, String lastName);

    @Query("SELECT r FROM Reservation r JOIN FETCH r.options WHERE r.status = :status")
    List<Reservation> findAllByStatusWithEagerFetch(@Param("status") Reservation.Status status);

}
