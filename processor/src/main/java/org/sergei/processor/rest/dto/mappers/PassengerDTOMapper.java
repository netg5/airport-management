package org.sergei.processor.rest.dto.mappers;

import org.sergei.processor.jpa.model.Passenger;
import org.sergei.processor.rest.dto.PassengerDTO;
import org.sergei.processor.utils.IMapper;
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
