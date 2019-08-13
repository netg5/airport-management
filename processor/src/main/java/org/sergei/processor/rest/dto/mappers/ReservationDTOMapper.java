package org.sergei.processor.rest.dto.mappers;

import org.sergei.processor.jpa.model.Reservation;
import org.sergei.processor.rest.dto.ReservationDTO;
import org.sergei.processor.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationDTO> {

    private final RouteDTOMapper routeDTOMapper;
    private final PassengerDTOMapper passengerDTOMapper;

    @Autowired
    public ReservationDTOMapper(RouteDTOMapper routeDTOMapper,
                                PassengerDTOMapper passengerDTOMapper) {
        this.routeDTOMapper = routeDTOMapper;
        this.passengerDTOMapper = passengerDTOMapper;
    }

    @Override
    public ReservationDTO apply(Reservation reservation) {
        return ReservationDTO.builder()
                .reservationId(reservation.getId())
                .dateOfFlying(reservation.getDateOfFlying())
                .departureTime(reservation.getDepartureTime())
                .arrivalTime(reservation.getArrivalTime())
                .hoursFlying(reservation.getHoursFlying())
                .passenger(passengerDTOMapper.apply(reservation.getPassenger()))
                .route(routeDTOMapper.apply(reservation.getRoute()))
                .build();
    }
}