package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.Warehouse;
import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class WarehouseDTOMapper implements IMapper<Warehouse, WarehouseDTO> {

    @Override
    public WarehouseDTO apply(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .city(warehouse.getCity())
                .country(warehouse.getCountry())
                .dangerous(warehouse.getDangerous())
                .heavyCargo(warehouse.getHeavyCargo())
                .keepCoolService(warehouse.getKeepCoolService())
                .liveAnimals(warehouse.getLiveAnimals())
                .valuableCargo(warehouse.getValuableCargo())
                .warehouseHanding(warehouse.getWarehouseHanding())
                .build();
    }
}
