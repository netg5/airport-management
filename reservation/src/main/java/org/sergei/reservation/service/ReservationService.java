package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface ReservationService {
    ResponseEntity<ResponseDTO<ReservationResponseDTO>> findOneForPassenger(Long passengerId, Long reservationId);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForPassenger(Long passengerId);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> saveReservation(ReservationDTO request);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> updateReservation(Long passengerId, Long reservationId,
                                                                          Map<String, Object> params);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> deleteReservation(Long passengerId, Long reservationId);
}
