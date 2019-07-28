package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.rest.dto.PassengerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class PassengerDTOListMapper implements IMapper<List<Passenger>, List<PassengerResponseDTO>> {

    private final PassengerDTOMapper passengerDTOMapper;

    @Autowired
    public PassengerDTOListMapper(PassengerDTOMapper passengerDTOMapper) {
        this.passengerDTOMapper = passengerDTOMapper;
    }

    @Override
    public List<PassengerResponseDTO> apply(List<Passenger> passengers) {
        return passengerDTOMapper.applyList(passengers);
    }
}
