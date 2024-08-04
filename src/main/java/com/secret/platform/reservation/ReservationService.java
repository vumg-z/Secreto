package com.secret.platform.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    ReservationResponseDTO saveReservation(CreateReservationRequestDTO reservationRequestDTO);

    Optional<Reservation> getReservationById(Long reservationId);

    List<Reservation> getAllReservations();

    void deleteReservation(Long reservationId);
}
