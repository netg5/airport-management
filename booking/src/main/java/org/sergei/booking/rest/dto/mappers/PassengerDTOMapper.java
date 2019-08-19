package org.sergei.booking.rest.dto.mappers;

import org.sergei.booking.jpa.model.Passenger;
import org.sergei.booking.rest.dto.PassengerDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PassengerDTOMapper implements IMapper<Passenger, PassengerDTO> {

    @Override
    public PassengerDTO apply(Passenger passenger) {
        return PassengerDTO.builder()
                .passengerId(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .age(passenger.getAge())
                .gender(passenger.getGender())
                .phone(passenger.getPhone())
                .build();
    }
}
