package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.ReservationRequestDTO;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface ReservationService {
    ResponseEntity<ResponseDTO<ReservationResponseDTO>> findOneForCustomer(Long customerId, Long reservationId);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForCustomer(Long customerId);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForCustomerPaginated(Long customerId,
                                                                                    int page, int size);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> saveReservation(Long customerId, ReservationRequestDTO request);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> updateReservation(Long customerId, Long reservationId,
                                                                          Map<String, Object> params);

    ResponseEntity<ResponseDTO<ReservationResponseDTO>> deleteReservation(Long customerId, Long reservationId);
}
