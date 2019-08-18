package org.sergei.booking.rest.dto.mappers;

import org.sergei.booking.jpa.model.Passenger;
import org.sergei.booking.rest.dto.PassengerDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class PassengerDTOListMapper implements IMapper<List<Passenger>, List<PassengerDTO>> {

    private final PassengerDTOMapper passengerDTOMapper;

    @Autowired
    public PassengerDTOListMapper(PassengerDTOMapper passengerDTOMapper) {
        this.passengerDTOMapper = passengerDTOMapper;
    }

    @Override
    public List<PassengerDTO> apply(List<Passenger> passengers) {
        return passengerDTOMapper.applyList(passengers);
    }
}
