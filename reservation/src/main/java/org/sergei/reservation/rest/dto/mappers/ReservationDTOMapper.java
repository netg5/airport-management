package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationDTO> {

    @Override
    public ReservationDTO apply(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setReservationId(reservation.getId());
        reservationDTO.setCustomerId(reservation.getCustomer().getId());
        reservationDTO.setRouteId(reservation.getRoute().getId());
        reservationDTO.setReservationDate(reservation.getReservationDate());

        return reservationDTO;
    }
}
