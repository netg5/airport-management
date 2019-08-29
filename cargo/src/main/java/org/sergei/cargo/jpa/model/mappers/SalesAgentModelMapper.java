package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.SalesAgent;
import org.sergei.cargo.rest.dto.SalesAgentDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class SalesAgentModelMapper implements IMapper<SalesAgentDTO, SalesAgent> {

    @Override
    public SalesAgent apply(SalesAgentDTO salesAgentDTO) {
        return SalesAgent.builder()
                .city(salesAgentDTO.getCity())
                .country(salesAgentDTO.getCountry())
                .email(salesAgentDTO.getEmail())
                .phone(salesAgentDTO.getPhone())
                .representative(salesAgentDTO.getRepresentative())
                .build();
    }
}
