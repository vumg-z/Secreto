package com.secret.platform.reservation;

public interface ReservationService {
    ReservationResponseDTO processReservation(CreateReservationRequestDTO reservationRequestDTO, String currency);
}
