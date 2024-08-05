package com.secret.platform.reservations;

import com.secret.platform.customer.Customer;
import com.secret.platform.customer.CustomerDTO;
import com.secret.platform.reservation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CancelReservationTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCancelReservationSuccess() {
        // Arrange
        String reservationNumber = "EAPPG346744";
        String lastName = "Mendoza";

        // Create a mock customer
        Customer customer = new Customer(1L, "Victor", "Mendoza", "victor.mendoza@example.com", "1234567890", "0987654321");

        // Create a mock reservation
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setConfirmationNumber(reservationNumber);
        reservation.setCustomer(customer);
        reservation.setStatus(Reservation.Status.ACTIVE);

        // Mock repository behavior
        when(reservationRepository.findByConfirmationNumberAndCustomerLastName(reservationNumber, lastName))
                .thenReturn(Optional.of(reservation));

        // Act
        CancelReservationRequestDTO cancelRequestDTO = new CancelReservationRequestDTO(
                "e46d774e-dc1d-47b1-9ac5-7246f17891fb",
                "2.3101",
                new CancelReservationRequestDTO.CancelReservationRequest(reservationNumber, new CustomerDTO(
                        customer.getId(),
                        new CustomerDTO.RenterName(customer.getFirstName(), customer.getLastName()),
                        new CustomerDTO.Address(customer.getEmail(), customer.getWorkTelephoneNumber(), customer.getCellTelephoneNumber())
                ))
        );

        reservationService.cancelReservation(cancelRequestDTO);

        // Assert
        verify(reservationRepository, times(1)).findByConfirmationNumberAndCustomerLastName(reservationNumber, lastName);
        assertEquals(Reservation.Status.CANCELED, reservation.getStatus());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testCancelReservationAlreadyCanceled() {
        // Arrange
        String reservationNumber = "EAPPG346744";
        String lastName = "Mendoza";

        // Create a mock customer
        Customer customer = new Customer(1L, "Victor", "Mendoza", "victor.mendoza@example.com", "1234567890", "0987654321");

        // Create a mock reservation
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setConfirmationNumber(reservationNumber);
        reservation.setCustomer(customer);
        reservation.setStatus(Reservation.Status.CANCELED); // Already canceled

        // Mock repository behavior
        when(reservationRepository.findByConfirmationNumberAndCustomerLastName(reservationNumber, lastName))
                .thenReturn(Optional.of(reservation));

        // Act
        CancelReservationRequestDTO cancelRequestDTO = new CancelReservationRequestDTO(
                "e46d774e-dc1d-47b1-9ac5-7246f17891fb",
                "2.3101",
                new CancelReservationRequestDTO.CancelReservationRequest(reservationNumber, new CustomerDTO(
                        customer.getId(),
                        new CustomerDTO.RenterName(customer.getFirstName(), customer.getLastName()),
                        new CustomerDTO.Address(customer.getEmail(), customer.getWorkTelephoneNumber(), customer.getCellTelephoneNumber())
                ))
        );

        reservationService.cancelReservation(cancelRequestDTO);

        // Assert
        verify(reservationRepository, times(1)).findByConfirmationNumberAndCustomerLastName(reservationNumber, lastName);
        assertEquals(Reservation.Status.CANCELED, reservation.getStatus());
        verify(reservationRepository, never()).save(reservation); // Ensure save is not called
    }

    @Test
    public void testCancelReservationNotFound() {
        // Arrange
        String reservationNumber = "EAPPG346744";
        String lastName = "Mendoza";

        // Mock repository behavior
        when(reservationRepository.findByConfirmationNumberAndCustomerLastName(reservationNumber, lastName))
                .thenReturn(Optional.empty());

        // Act & Assert
        CancelReservationRequestDTO cancelRequestDTO = new CancelReservationRequestDTO(
                "e46d774e-dc1d-47b1-9ac5-7246f17891fb",
                "2.3101",
                new CancelReservationRequestDTO.CancelReservationRequest(reservationNumber, new CustomerDTO(
                        1L,
                        new CustomerDTO.RenterName("Victor", lastName),
                        new CustomerDTO.Address("victor.mendoza@example.com", "1234567890", "0987654321")
                ))
        );

        assertThrows(RuntimeException.class, () -> reservationService.cancelReservation(cancelRequestDTO));

        verify(reservationRepository, times(1)).findByConfirmationNumberAndCustomerLastName(reservationNumber, lastName);
    }
}
