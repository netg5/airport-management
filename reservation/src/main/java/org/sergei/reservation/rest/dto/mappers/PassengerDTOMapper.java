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

        PassengerResponseDTO passengerResponseDTO = new PassengerResponseDTO();

        passengerResponseDTO.setPassengerId(passenger.getId());
        passengerResponseDTO.setFirstName(passenger.getFirstName());
        passengerResponseDTO.setLastName(passenger.getLastName());
        passengerResponseDTO.setAge(passenger.getAge());

        return passengerResponseDTO;
    }
}
