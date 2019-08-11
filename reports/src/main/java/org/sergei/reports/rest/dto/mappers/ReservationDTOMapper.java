package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.Reservation;
import org.sergei.reports.rest.dto.ReservationDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationDTO> {

    @Override
    public ReservationDTO apply(Reservation reservation) {

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setCustomerId(reservation.getPassengerId());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setReservationId(reservation.getReservationId());
        reservationDTO.setRouteId(reservation.getRouteId());

        return reservationDTO;
    }
}
