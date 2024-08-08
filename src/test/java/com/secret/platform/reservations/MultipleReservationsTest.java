package com.secret.platform.reservations;

import com.secret.platform.customer.Customer;
import com.secret.platform.customer.CustomerDTO;
import com.secret.platform.customer.CustomerRepository;
import com.secret.platform.reservation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MultipleReservationsTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    // Declare expectedReservations as a class-level field
    private List<Reservation> expectedReservations;

    private CreateReservationRequestDTO createReservationRequestDTO(String email) {
        CreateReservationRequestDTO.NewReservationRequest newReservationRequest = new CreateReservationRequestDTO.NewReservationRequest(
                false,
                new CreateReservationRequestDTO.NewReservationRequest.Pickup("GDLMY1", LocalDateTime.of(2024, 8, 21, 10, 0)),
                new CreateReservationRequestDTO.NewReservationRequest.Return("GDLMY1", LocalDateTime.of(2024, 8, 22, 10, 0)),
                new CreateReservationRequestDTO.NewReservationRequest.Source("e46d774e-dc1d-47b1-9ac5-7246f17891fb", "US"),
                new CreateReservationRequestDTO.NewReservationRequest.Vehicle("ECAR"),
                new CustomerDTO(
                        null,
                        new CustomerDTO.RenterName("victor", "mendoza"),
                        new CustomerDTO.Address(email, "1234567890", "0987654321")
                ),
                new CreateReservationRequestDTO.NewReservationRequest.QuotedRate(
                        "ERC", // productCode
                        "20060223331430ECAR", // rateID
                        "ECAR", // classCode
                        "MXN", // currencyCode
                        "MYWEB1", // corporateRateID
                        null, // dayRate can be null
                        null, // weekRate
                        null, // monthRate can be null
                        null, // xdayRate
                        new CreateReservationRequestDTO.NewReservationRequest.QuotedRate.TotalCost(67.06, "MXN")
                ),
                "0987654321",
                List.of(
                        new CreateReservationRequestDTO.NewReservationRequest.Option("FC03", 1),
                        new CreateReservationRequestDTO.NewReservationRequest.Option("FC04", 2)
                ),
                new CreateReservationRequestDTO.NewReservationRequest.ReservationNotes(
                        "Cnf=EAPPG346744 SCAR REVISE RESERVA Si Prepaid o Inclusiva"
                )
        );

        return new CreateReservationRequestDTO("e46d774e-dc1d-47b1-9ac5-7246f17891fb", "2.3101", newReservationRequest);
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Initialize the expectedReservations list
        expectedReservations = new ArrayList<>();

        // Mock findByCustomerAndStatus to return expected reservations for each test email
        when(reservationRepository.findByCustomerAndStatus(any(Customer.class), eq(Reservation.Status.ACTIVE)))
                .thenAnswer(invocation -> {
                    Customer customer = invocation.getArgument(0);
                    return expectedReservations.stream()
                            .filter(reservation -> reservation.getCustomer().getEmail().equals(customer.getEmail())
                                    && reservation.getStatus() == Reservation.Status.ACTIVE)
                            .collect(Collectors.toList());
                });
    }

    @Test
    public void testCreateAndFindMultipleReservations() {
        List<String> emails = new ArrayList<>();

        // Prepare the test data
        for (int i = 0; i < 5; i++) {
            String email = "user" + i + "@example.com";
            emails.add(email);

            // Mock customer existence
            Customer customer = new Customer((long) i + 1, "victor", "mendoza", email, "1234567890", "0987654321");
            when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

            // Mock reservation
            Reservation reservation = new Reservation();
            reservation.setId((long) i + 1);
            reservation.setCustomer(customer);
            reservation.setReferenceNumber("e46d774e-dc1d-47b1-9ac5-7246f17891fb-" + i);  // Unique reference number
            reservation.setStatus(Reservation.Status.ACTIVE); // Set status to ACTIVE
            expectedReservations.add(reservation);

            // Mock saving reservation
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        }

        // Test reservation creation
        for (String email : emails) {
            CreateReservationRequestDTO requestDTO = createReservationRequestDTO(email);
            ReservationResponseDTO response = reservationService.saveReservation(requestDTO, "USD");

            assertNotNull(response);
            assertTrue(response.getNewReservationResponse().isReservationConfirmed());
            assertEquals("Confirmed", response.getNewReservationResponse().getReservationStatus());
        }

        // Verify reservations were created
        for (String email : emails) {
            List<ReservationDTO> reservationDTOs = reservationService.getReservationsByCustomerEmail(email);
            assertFalse(reservationDTOs.isEmpty(), "Reservations should not be empty for " + email);
            assertTrue(reservationDTOs.stream().anyMatch(res -> res.getCustomer().getAddress().getEmail().equals(email)),
                    "Reservation for email " + email + " not found.");
        }

        // Adjust verification to allow for two calls (one for save and one for verification) per reservation
        verify(reservationRepository, times(5)).findByCustomerAndStatus(any(Customer.class), eq(Reservation.Status.ACTIVE));

        // Verify all mock interactions
        verify(customerRepository, times(10)).findByEmail(anyString());
        verify(reservationRepository, times(5)).save(any(Reservation.class));
    }
}
