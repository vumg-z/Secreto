package com.secret.platform.reservation;

import com.secret.platform.customer.Customer;
import com.secret.platform.customer.CustomerDTO;
import com.secret.platform.customer.CustomerMapper;
import com.secret.platform.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ReservationResponseDTO saveReservation(CreateReservationRequestDTO reservationRequestDTO) {
        return null;
    }

    @Override
    public ReservationResponseDTO saveReservation(CreateReservationRequestDTO reservationRequestDTO, String currency) {
        logger.info("Saving reservation...");

        // Extract necessary information from the request DTO
        CreateReservationRequestDTO.NewReservationRequest request = reservationRequestDTO.getNewReservationRequest();

        // Retrieve or create the customer from the DTO
        Customer customer = findOrCreateCustomer(request.getRenter());

        // Create a new reservation entity
        Reservation reservation = new Reservation();
        reservation.setReferenceNumber(reservationRequestDTO.getReferenceNumber());
        reservation.setVersion(reservationRequestDTO.getVersion());
        reservation.setConfirmAvailability(request.isConfirmAvailability());
        reservation.setPickupLocationCode(request.getPickup().getLocationCode());
        reservation.setPickupDateTime(request.getPickup().getDateTime());
        reservation.setReturnLocationCode(request.getReturnInfo().getLocationCode());
        reservation.setReturnDateTime(request.getReturnInfo().getDateTime());
        reservation.setSourceCountryCode(request.getSource().getCountryCode());
        reservation.setConfirmationNumber(request.getSource().getConfirmationNumber());
        reservation.setVehicleClassCode(request.getVehicle().getClassCode());
        reservation.setCurrencyCode(currency);
        reservation.setCorporateRateID(request.getQuotedRate().getCorporateRateID());
        reservation.setTotalCostAmount(request.getQuotedRate().getTotalCost().getAmount());
        reservation.setLocalPhone(request.getLocalPhone());
        reservation.setCustomer(customer);
        reservation.setStatus(Reservation.Status.ACTIVE);

        // Handle reservation notes
        if (request.getReservationNotes() != null) {
            reservation.setReservationNotes(request.getReservationNotes().getNote());
        }

        // Handle options list safely
        reservation.setOptions(convertOptionsToEntity(request.getOptions()));

        // Handle quoted rate details
        CreateReservationRequestDTO.NewReservationRequest.QuotedRate quotedRate = request.getQuotedRate();
        if (quotedRate != null) {
            if (quotedRate.getProductCode() != null) {
                reservation.setProductCode(quotedRate.getProductCode());
            }

            // Use default values when the Double fields are null
            reservation.setDayRate(getOrDefault(quotedRate.getDayRate(), -10.0));
            reservation.setWeekRate(getOrDefault(quotedRate.getWeekRate(), -10.0));
            reservation.setMonthRate(getOrDefault(quotedRate.getMonthRate(), -10.0));
            reservation.setXdayRate(getOrDefault(quotedRate.getXdayRate(), -10.0));
        }

        // Save the reservation to the database
        Reservation savedReservation = reservationRepository.save(reservation);
        logger.info("Reservation saved with ID: {}", savedReservation.getId());

        // Build and return the response DTO
        return createReservationResponse(savedReservation);
    }

    private Customer findOrCreateCustomer(CustomerDTO customerDTO) {
        // Check if the customer already exists in the database
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerDTO.getAddress().getEmail());
        if (existingCustomer.isPresent()) {
            logger.info("Existing customer found: {}", existingCustomer.get());
            return existingCustomer.get();
        }

        // If not found, create a new customer
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("New customer created: {}", savedCustomer);
        return savedCustomer;
    }

    private List<Reservation.ReservationOption> convertOptionsToEntity(List<CreateReservationRequestDTO.NewReservationRequest.Option> options) {
        if (options == null) {
            return Collections.emptyList(); // Initialize with an empty list if options are null
        }
        return options.stream()
                .map(option -> new Reservation.ReservationOption(option.getCode(), option.getQuantity()))
                .collect(Collectors.toList());
    }

    private ReservationResponseDTO createReservationResponse(Reservation reservation) {
        ReservationResponseDTO response = new ReservationResponseDTO();

        // Populate the response with details from the reservation
        ReservationResponseDTO.NewReservationResponse newReservationResponse = new ReservationResponseDTO.NewReservationResponse();
        newReservationResponse.setSuccess(true);
        newReservationResponse.setReservationStatus("Confirmed");
        newReservationResponse.setReservationNumber(reservation.getConfirmationNumber());
        newReservationResponse.setReservationConfirmed(true);

        response.setRegardingReferenceNumber(reservation.getReferenceNumber());
        response.setVersion(reservation.getVersion());
        response.setWebxgId(UUID.randomUUID().toString());
        response.setNewReservationResponse(newReservationResponse);

        logger.info("Created reservation response: {}", response);
        return response;
    }

    @Override
    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findByIdAndStatus(reservationId, Reservation.Status.ACTIVE);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAllByStatus(Reservation.Status.ACTIVE);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent() && reservation.get().getStatus() == Reservation.Status.ACTIVE) {
            reservation.get().setStatus(Reservation.Status.CANCELED);
            reservationRepository.save(reservation.get());
            logger.info("Canceled reservation with ID: {}", reservationId);
        } else {
            logger.warn("Reservation with ID {} does not exist or is already canceled", reservationId);
        }
    }

    public List<Reservation> getReservationsByCustomerName(String firstName, String lastName) {
        logger.info("Searching for active reservations by customer name: {} {}", firstName, lastName);

        List<Customer> customers = customerRepository.findByFirstNameAndLastName(firstName, lastName);

        List<Reservation> reservations = customers.stream()
                .flatMap(customer -> reservationRepository.findByCustomerAndStatus(customer, Reservation.Status.ACTIVE).stream())
                .collect(Collectors.toList());

        logger.info("Found {} active reservations for customer {} {}", reservations.size(), firstName, lastName);
        return reservations;
    }

    public List<Reservation> getReservationsByCustomerEmail(String email) {
        logger.info("Searching for active reservations by customer email: {}", email);

        Optional<Customer> customer = customerRepository.findByEmail(email);

        if (customer.isPresent()) {
            List<Reservation> reservations = reservationRepository.findByCustomerAndStatus(customer.get(), Reservation.Status.ACTIVE);

            logger.info("Found {} active reservations for customer email {}", reservations.size(), email);
            return reservations;
        } else {
            logger.warn("No customer found with email {}", email);
            return Collections.emptyList();
        }
    }

    public List<Reservation> getReservationsByCustomerPhoneNumber(String phoneNumber) {
        logger.info("Searching for active reservations by customer phone number: {}", phoneNumber);

        List<Customer> customers = customerRepository.findByWorkTelephoneNumberOrCellTelephoneNumber(phoneNumber, phoneNumber);

        List<Reservation> reservations = customers.stream()
                .flatMap(customer -> reservationRepository.findByCustomerAndStatus(customer, Reservation.Status.ACTIVE).stream())
                .collect(Collectors.toList());

        logger.info("Found {} active reservations for phone number {}", reservations.size(), phoneNumber);
        return reservations;
    }

    // Helper method to provide a default value
    private double getOrDefault(Double value, double defaultValue) {
        return value != null ? value : defaultValue;
    }

    public void cancelReservation(CancelReservationRequestDTO cancelRequestDTO) {
        String reservationNumber = cancelRequestDTO.getCancelReservationRequest().getReservationNumber();
        String lastName = cancelRequestDTO.getCancelReservationRequest().getCustomer().getRenterName().getLastName();

        // Find the reservation by reservation number and customer's last name
        Optional<Reservation> optionalReservation = reservationRepository.findByConfirmationNumberAndCustomerLastName(reservationNumber, lastName);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            if (reservation.getStatus() != Reservation.Status.CANCELED) {
                reservation.setStatus(Reservation.Status.CANCELED);
                reservationRepository.save(reservation);
                logger.info("Reservation {} canceled successfully.", reservationNumber);
            } else {
                logger.warn("Reservation {} is already canceled.", reservationNumber);
            }
        } else {
            logger.error("Reservation {} not found or customer last name does not match.", reservationNumber);
            throw new RuntimeException("Reservation not found or customer details incorrect.");
        }
    }
}
