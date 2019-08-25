package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.Cargo;
import org.sergei.cargo.rest.dto.CargoDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoDTOMapper implements IMapper<Cargo, CargoDTO> {

    private final SalesAgentDTOMapper salesAgentDTOMapper;
    private final WarehouseDTOMapper warehouseDTOMapper;

    @Autowired
    public CargoDTOMapper(SalesAgentDTOMapper salesAgentDTOMapper,
                          WarehouseDTOMapper warehouseDTOMapper) {
        this.salesAgentDTOMapper = salesAgentDTOMapper;
        this.warehouseDTOMapper = warehouseDTOMapper;
    }

    @Override
    public CargoDTO apply(Cargo cargo) {
        return CargoDTO.builder()
                .height(cargo.getHeight())
                .internalVolume(cargo.getInternalVolume())
                .length(cargo.getLength())
                .maxGrossWeight(cargo.getMaxGrossWeight())
                .tareWeight(cargo.getTareWeight())
                .unitType(cargo.getUnitType())
                .weight(cargo.getWeight())
                .salesAgent(salesAgentDTOMapper.apply(cargo.getSalesAgent()))
                .warehouse(warehouseDTOMapper.apply(cargo.getWarehouse()))
                .build();
    }
}
