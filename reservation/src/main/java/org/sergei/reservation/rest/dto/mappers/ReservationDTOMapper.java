package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationDTO> {

    private final RouteDTOMapper routeDTOMapper;

    @Autowired
    public ReservationDTOMapper(RouteDTOMapper routeDTOMapper) {
        this.routeDTOMapper = routeDTOMapper;
    }

    @Override
    public ReservationDTO apply(Reservation reservation) {
        return ReservationDTO.builder()
                .reservationId(reservation.getId())
                .dateOfFlying(reservation.getDateOfFlying())
                .departureTime(reservation.getDepartureTime())
                .arrivalTime(reservation.getArrivalTime())
                .hoursFlying(reservation.getHoursFlying())
                .route(routeDTOMapper.apply(reservation.getRoute()))
                .build();
    }
}
