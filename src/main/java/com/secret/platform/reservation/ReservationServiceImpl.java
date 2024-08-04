package com.secret.platform.reservation;

import com.secret.platform.customer.Customer;
import com.secret.platform.customer.CustomerDTO;
import com.secret.platform.customer.CustomerMapper;
import com.secret.platform.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        logger.info("Saving reservation...");

        // Extract necessary information from the request DTO
        CreateReservationRequestDTO.NewReservationRequest request = reservationRequestDTO.getNewReservationRequest();

        // Retrieve or create the customer from the DTO
        Customer customer = findOrCreateCustomer(request.getCustomer());

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
        reservation.setCurrencyCode(request.getQuotedRate().getCurrencyCode());
        reservation.setCorporateRateID(request.getQuotedRate().getCorporateRateID());
        reservation.setTotalCostAmount(request.getQuotedRate().getTotalCost().getAmount());
        reservation.setLocalPhone(request.getLocalPhone());
        reservation.setCustomer(customer);
        reservation.setOptions(convertOptionsToEntity(request.getOptions()));

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
        return reservationRepository.findById(reservationId);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            logger.warn("Reservation with ID {} does not exist", reservationId);
            return;
        }
        reservationRepository.deleteById(reservationId);
        logger.info("Deleted reservation with ID: {}", reservationId);
    }
}
