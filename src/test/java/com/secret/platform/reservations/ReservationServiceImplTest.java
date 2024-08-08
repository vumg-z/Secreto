package com.secret.platform.reservations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.secret.platform.reservation.*;
import com.secret.platform.customer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReservationById() {
        // Arrange
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setConfirmationNumber("CONF123");
        reservation.setStatus(Reservation.Status.ACTIVE);

        when(reservationRepository.findByIdAndStatus(reservationId, Reservation.Status.ACTIVE))
                .thenReturn(Optional.of(reservation));

        // Act
        Optional<Reservation> result = reservationService.getReservationById(reservationId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("CONF123", result.get().getConfirmationNumber());
        verify(reservationRepository, times(1)).findByIdAndStatus(reservationId, Reservation.Status.ACTIVE);
    }

    @Test
    public void testGetReservationsByCustomerName() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail("johndoe@example.com");

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setConfirmationNumber("CONF123");
        reservation.setStatus(Reservation.Status.ACTIVE);

        when(customerRepository.findByFirstNameAndLastName(firstName, lastName))
                .thenReturn(List.of(customer));
        when(reservationRepository.findByCustomerAndStatus(customer, Reservation.Status.ACTIVE))
                .thenReturn(List.of(reservation));

        // Act
        List<ReservationDTO> result = reservationService.getReservationsByCustomerName(firstName, lastName);

        // Assert
        assertEquals(1, result.size());
        assertEquals("CONF123", result.get(0).getConfirmationNumber());
        verify(customerRepository, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(reservationRepository, times(1)).findByCustomerAndStatus(customer, Reservation.Status.ACTIVE);
    }

    @Test
    public void testGetReservationsByCustomerEmail() {
        // Arrange
        String email = "johndoe@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setConfirmationNumber("CONF123");
        reservation.setStatus(Reservation.Status.ACTIVE);

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        when(reservationRepository.findByCustomerAndStatus(customer, Reservation.Status.ACTIVE))
                .thenReturn(List.of(reservation));

        // Act
        List<ReservationDTO> result = reservationService.getReservationsByCustomerEmail(email);

        // Assert
        assertEquals(1, result.size());
        assertEquals("CONF123", result.get(0).getConfirmationNumber());
        verify(customerRepository, times(1)).findByEmail(email);
        verify(reservationRepository, times(1)).findByCustomerAndStatus(customer, Reservation.Status.ACTIVE);
    }

    @Test
    public void testGetReservationsByCustomerPhoneNumber() {
        // Arrange
        String phoneNumber = "1234567890";
        Customer customer = new Customer();
        customer.setWorkTelephoneNumber(phoneNumber);
        customer.setEmail("johndoe@example.com");

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setConfirmationNumber("CONF123");
        reservation.setStatus(Reservation.Status.ACTIVE);

        when(customerRepository.findByWorkTelephoneNumberOrCellTelephoneNumber(phoneNumber, phoneNumber))
                .thenReturn(List.of(customer));
        when(reservationRepository.findByCustomerAndStatus(customer, Reservation.Status.ACTIVE))
                .thenReturn(List.of(reservation));

        // Act
        List<ReservationDTO> result = reservationService.getReservationsByCustomerPhoneNumber(phoneNumber);

        // Assert
        assertEquals(1, result.size());
        assertEquals("CONF123", result.get(0).getConfirmationNumber());
        verify(customerRepository, times(1)).findByWorkTelephoneNumberOrCellTelephoneNumber(phoneNumber, phoneNumber);
        verify(reservationRepository, times(1)).findByCustomerAndStatus(customer, Reservation.Status.ACTIVE);
    }

    @Test
    public void testGetReservationsByCustomerName_NoResults() {
        // Arrange
        String firstName = "Jane";
        String lastName = "Smith";

        when(customerRepository.findByFirstNameAndLastName(firstName, lastName))
                .thenReturn(Collections.emptyList());

        // Act
        List<ReservationDTO> result = reservationService.getReservationsByCustomerName(firstName, lastName);

        // Assert
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(reservationRepository, never()).findByCustomerAndStatus(any(Customer.class), any(Reservation.Status.class));
    }

    @Test
    public void testGetReservationsByCustomerEmail_NoResults() {
        // Arrange
        String email = "janesmith@example.com";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        List<ReservationDTO> result = reservationService.getReservationsByCustomerEmail(email);

        // Assert
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findByEmail(email);
        verify(reservationRepository, never()).findByCustomerAndStatus(any(Customer.class), any(Reservation.Status.class));
    }

    @Test
    public void testGetReservationsByCustomerPhoneNumber_NoResults() {
        // Arrange
        String phoneNumber = "0987654321";

        when(customerRepository.findByWorkTelephoneNumberOrCellTelephoneNumber(phoneNumber, phoneNumber))
                .thenReturn(Collections.emptyList());

        // Act
        List<ReservationDTO> result = reservationService.getReservationsByCustomerPhoneNumber(phoneNumber);

        // Assert
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findByWorkTelephoneNumberOrCellTelephoneNumber(phoneNumber, phoneNumber);
        verify(reservationRepository, never()).findByCustomerAndStatus(any(Customer.class), any(Reservation.Status.class));
    }
}
