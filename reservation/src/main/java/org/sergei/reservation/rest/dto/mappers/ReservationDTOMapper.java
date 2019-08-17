package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationDTO> {

    private final RouteDTOMapper routeDTOMapper;
    private final PassengerDTOMapper passengerDTOMapper;
    private final FlyModeDTOMapper flyModeDTOMapper;

    @Autowired
    public ReservationDTOMapper(RouteDTOMapper routeDTOMapper,
                                PassengerDTOMapper passengerDTOMapper,
                                FlyModeDTOMapper flyModeDTOMapper) {
        this.routeDTOMapper = routeDTOMapper;
        this.passengerDTOMapper = passengerDTOMapper;
        this.flyModeDTOMapper = flyModeDTOMapper;
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
                .flyMode(flyModeDTOMapper.apply(reservation.getFlyMode()))
                .build();
    }
}
