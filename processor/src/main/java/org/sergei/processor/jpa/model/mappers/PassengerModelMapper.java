package org.sergei.processor.jpa.model.mappers;

import org.sergei.processor.jpa.model.Passenger;
import org.sergei.processor.rest.dto.PassengerDTO;
import org.sergei.processor.utils.IMapper;
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
