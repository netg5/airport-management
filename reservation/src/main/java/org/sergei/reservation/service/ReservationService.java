package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.rest.dto.request.ReservationDTORequest;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface ReservationService {

    ResponseEntity<ResponseDTO<ReservationDTO>> findAll();

    ResponseEntity<ResponseDTO<ReservationDTO>> findAllForPassenger(Long passengerId);

    ResponseEntity<ResponseDTO<ReservationDTO>> findOneForPassenger(Long passengerId, Long reservationId);

    ResponseEntity<ResponseDTO<ReservationDTO>> saveReservation(ReservationDTORequest request);

    ResponseEntity<ResponseDTO<ReservationDTO>> updateReservation(ReservationDTO request);

    ResponseEntity<ResponseDTO<ReservationDTO>> discardReservation(Long passengerId, Long reservationId);
}
