package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Airport;
import org.sergei.manager.rest.dto.AirportDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AirportDOTMapper implements IMapper<Airport, AirportDTO> {

    @Override
    public AirportDTO apply(Airport airport) {
        return AirportDTO.builder()
                .id(airport.getId())
                .airportName(airport.getAirportName())
                .airportAddress(airport.getAirportAddress())
                .contactJob(airport.getContactJob())
                .contactName(airport.getContactName())
                .country(airport.getCountry())
                .email(airport.getEmail())
                .phone(airport.getPhone())
                .build();
    }
}
