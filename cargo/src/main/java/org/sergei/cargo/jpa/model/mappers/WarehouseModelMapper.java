package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.Warehouse;
import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class WarehouseModelMapper implements IMapper<WarehouseDTO, Warehouse> {

    @Override
    public Warehouse apply(WarehouseDTO warehouseDTO) {
        return Warehouse.builder()
                .city(warehouseDTO.getCity())
                .country(warehouseDTO.getCountry())
                .dangerous(warehouseDTO.getDangerous())
                .heavyCargo(warehouseDTO.getHeavyCargo())
                .keepCoolService(warehouseDTO.getKeepCoolService())
                .liveAnimals(warehouseDTO.getLiveAnimals())
                .valuableCargo(warehouseDTO.getValuableCargo())
                .warehouseHanding(warehouseDTO.getWarehouseHanding())
                .build();
    }
}
