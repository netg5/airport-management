package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.SalesAgent;
import org.sergei.cargo.rest.dto.SalesAgentDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class SalesAgentDTOMapper implements IMapper<SalesAgent, SalesAgentDTO> {
    @Override
    public SalesAgentDTO apply(SalesAgent salesAgent) {
        return SalesAgentDTO.builder()
                .city(salesAgent.getCity())
                .country(salesAgent.getCountry())
                .email(salesAgent.getEmail())
                .phone(salesAgent.getPhone())
                .representative(salesAgent.getRepresentative())
                .build();
    }
}
