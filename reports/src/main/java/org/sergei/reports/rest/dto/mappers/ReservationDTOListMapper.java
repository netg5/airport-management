package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.Reservation;
import org.sergei.reports.rest.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class ReservationDTOListMapper implements IMapper<List<Reservation>, List<ReservationDTO>> {

    private final ReservationDTOMapper reservationDTOMapper;

    @Autowired
    public ReservationDTOListMapper(ReservationDTOMapper reservationDTOMapper) {
        this.reservationDTOMapper = reservationDTOMapper;
    }

    @Override
    public List<ReservationDTO> apply(List<Reservation> reservations) {
        return reservationDTOMapper.applyList(reservations);
    }
}
