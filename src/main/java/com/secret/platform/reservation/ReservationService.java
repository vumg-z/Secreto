package com.secret.platform.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    ReservationResponseDTO saveReservation(CreateReservationRequestDTO reservationRequestDTO);

    ReservationResponseDTO saveReservation(CreateReservationRequestDTO reservationRequestDTO, String currency);

    Optional<Reservation> getReservationById(Long reservationId);

    List<ReservationDTO> getAllReservations();
    void deleteReservation(Long reservationId);


}
