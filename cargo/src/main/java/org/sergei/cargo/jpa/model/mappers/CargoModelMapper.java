package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.Cargo;
import org.sergei.cargo.rest.dto.CargoDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoModelMapper implements IMapper<CargoDTO, Cargo> {

    private final SalesAgentModelMapper salesAgentModelMapper;
    private final WarehouseModelMapper warehouseModelMapper;

    @Autowired
    public CargoModelMapper(SalesAgentModelMapper salesAgentModelMapper,
                            WarehouseModelMapper warehouseModelMapper) {
        this.salesAgentModelMapper = salesAgentModelMapper;
        this.warehouseModelMapper = warehouseModelMapper;
    }

    @Override
    public Cargo apply(CargoDTO cargoDTO) {
        return Cargo.builder()
                .height(cargoDTO.getHeight())
                .internalVolume(cargoDTO.getInternalVolume())
                .length(cargoDTO.getLength())
                .maxGrossWeight(cargoDTO.getMaxGrossWeight())
                .tareWeight(cargoDTO.getTareWeight())
                .unitType(cargoDTO.getUnitType())
                .weight(cargoDTO.getWeight())
                .salesAgent(salesAgentModelMapper.apply(cargoDTO.getSalesAgent()))
                .warehouse(warehouseModelMapper.apply(cargoDTO.getWarehouse()))
                .build();
    }
}
