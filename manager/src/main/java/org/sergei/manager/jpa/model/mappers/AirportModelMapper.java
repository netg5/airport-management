package org.sergei.manager.jpa.model.mappers;

import org.sergei.manager.jpa.model.Airport;
import org.sergei.manager.rest.dto.AirportDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AirportModelMapper implements IMapper<AirportDTO, Airport> {

    @Override
    public Airport apply(AirportDTO airportDTO) {
        return Airport.builder()
                .airportName(airportDTO.getAirportName())
                .airportAddress(airportDTO.getAirportAddress())
                .contactJob(airportDTO.getContactJob())
                .contactName(airportDTO.getContactName())
                .country(airportDTO.getCountry())
                .email(airportDTO.getEmail())
                .phone(airportDTO.getPhone())
                .build();
    }
}
