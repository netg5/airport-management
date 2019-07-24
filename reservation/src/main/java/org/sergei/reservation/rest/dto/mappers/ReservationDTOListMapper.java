package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOListMapper implements IMapper<List<Reservation>, List<ReservationResponseDTO>> {

    private final ReservationDTOMapper reservationDTOMapper;

    @Autowired
    public ReservationDTOListMapper(ReservationDTOMapper reservationDTOMapper) {
        this.reservationDTOMapper = reservationDTOMapper;
    }

    @Override
    public List<ReservationResponseDTO> apply(List<Reservation> reservations) {
        return reservationDTOMapper.applyList(reservations);
    }
}
