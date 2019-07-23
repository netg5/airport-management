package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOMapper implements IMapper<Reservation, ReservationResponseDTO> {

    private final RouteDTOMapper routeDTOMapper;

    @Autowired
    public ReservationDTOMapper(RouteDTOMapper routeDTOMapper) {
        this.routeDTOMapper = routeDTOMapper;
    }

    @Override
    public ReservationResponseDTO apply(Reservation reservation) {
        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();

        reservationResponseDTO.setReservationId(reservation.getId());
        reservationResponseDTO.setCustomerId(reservation.getCustomer().getId());
        reservationResponseDTO.setReservationDate(reservation.getReservationDate());
        reservationResponseDTO.setRoutes(routeDTOMapper.apply(reservation.getRoute()));

        return reservationResponseDTO;
    }
}
