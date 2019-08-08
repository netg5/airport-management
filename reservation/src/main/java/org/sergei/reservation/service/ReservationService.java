package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface ReservationService {
    ResponseEntity<ResponseDTO<ReservationDTO>> findOneForPassenger(Long passengerId, Long reservationId);

    ResponseEntity<ResponseDTO<ReservationDTO>> findAllForPassenger(Long passengerId);

    ResponseEntity<ResponseDTO<ReservationDTO>> saveReservation(ReservationDTO request);

    ResponseEntity<ResponseDTO<ReservationDTO>> updateReservation(Long passengerId, Long reservationId,
                                                                  Map<String, Object> params);

    ResponseEntity<ResponseDTO<ReservationDTO>> deleteReservation(Long passengerId, Long reservationId);
}
