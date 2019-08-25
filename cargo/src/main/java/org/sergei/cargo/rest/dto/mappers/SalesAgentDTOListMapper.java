package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.SalesAgent;
import org.sergei.cargo.rest.dto.SalesAgentDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class SalesAgentDTOListMapper implements IMapper<List<SalesAgent>, List<SalesAgentDTO>> {

    private final SalesAgentDTOMapper salesAgentDTOMapper;

    @Autowired
    public SalesAgentDTOListMapper(SalesAgentDTOMapper salesAgentDTOMapper) {
        this.salesAgentDTOMapper = salesAgentDTOMapper;
    }

    @Override
    public List<SalesAgentDTO> apply(List<SalesAgent> salesAgents) {
        return salesAgentDTOMapper.applyList(salesAgents);
    }
}
