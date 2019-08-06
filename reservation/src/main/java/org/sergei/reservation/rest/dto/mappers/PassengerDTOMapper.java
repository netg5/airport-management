package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.rest.dto.PassengerResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PassengerDTOMapper implements IMapper<Passenger, PassengerResponseDTO> {

    @Override
    public PassengerResponseDTO apply(Passenger passenger) {
        return PassengerResponseDTO.builder()
                .passengerId(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .age(passenger.getAge())
                .gender(passenger.getGender())
                .phone(passenger.getPhone())
                .build();
    }
}
