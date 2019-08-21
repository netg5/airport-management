package org.sergei.booking.jpa.model.mapper;

import org.sergei.booking.jpa.model.Passenger;
import org.sergei.booking.rest.dto.PassengerDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PassengerModelMapper implements IMapper<PassengerDTO, Passenger> {

    @Override
    public Passenger apply(PassengerDTO passengerDTO) {
        return Passenger.builder()
                .firstName(passengerDTO.getFirstName())
                .lastName(passengerDTO.getLastName())
                .age(passengerDTO.getAge())
                .gender(passengerDTO.getGender())
                .phone(passengerDTO.getPhone())
                .build();
    }
}
