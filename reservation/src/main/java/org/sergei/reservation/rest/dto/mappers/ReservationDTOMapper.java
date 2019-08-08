package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationResponseDTO> {

    @Override
    public ReservationResponseDTO apply(Reservation reservation) {
        return ReservationResponseDTO.builder()
                .customerId(reservation.getId())
                .dateOfFlying(reservation.getDateOfFlying())
                .departureTime(reservation.getDepartureTime())
                .arrivalTime(reservation.getArrivalTime())
                .hoursFlying(reservation.getHoursFlying())
                .aircraftId(reservation.getRoute().getId())
                .build();
    }
}
