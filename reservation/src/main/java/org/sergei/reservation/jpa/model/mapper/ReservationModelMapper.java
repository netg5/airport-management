package org.sergei.reservation.jpa.model.mapper;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationModelMapper implements IMapper<ReservationDTO, Reservation> {

    private final PassengerModelMapper passengerModelMapper;
    private final RouteModelMapper routeModelMapper;

    @Autowired
    public ReservationModelMapper(PassengerModelMapper passengerModelMapper,
                                  RouteModelMapper routeModelMapper) {
        this.passengerModelMapper = passengerModelMapper;
        this.routeModelMapper = routeModelMapper;
    }

    @Override
    public Reservation apply(ReservationDTO reservationDTO) {
        return Reservation.builder()
                .departureTime(reservationDTO.getDepartureTime())
                .arrivalTime(reservationDTO.getArrivalTime())
                .dateOfFlying(reservationDTO.getDateOfFlying())
                .hoursFlying(reservationDTO.getHoursFlying())
                .passenger(passengerModelMapper.apply(reservationDTO.getPassenger()))
                .route(routeModelMapper.apply(reservationDTO.getRoute()))
                .build();
    }
}
