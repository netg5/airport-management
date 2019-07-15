package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.rest.dto.ReservationExtendedDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface ReservationService {
    ReservationExtendedDTO findOneForCustomer(Long customerId, Long reservationId);

    List<ReservationExtendedDTO> findAllForCustomer(Long customerId);

    Page<ReservationExtendedDTO> findAllForCustomerPaginated(Long customerId, int page, int size);

    ReservationDTO saveReservation(Long customerId,
                                   ReservationDTO reservationDTO);

    ReservationDTO updateReservation(Long customerId, Long reservationId, Map<String, Object> params);

    ReservationExtendedDTO deleteReservation(Long customerId, Long reservationId);
}
