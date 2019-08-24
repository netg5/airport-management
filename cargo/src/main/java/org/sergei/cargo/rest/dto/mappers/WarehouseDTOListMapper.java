package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.Warehouse;
import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class WarehouseDTOListMapper implements IMapper<List<Warehouse>, List<WarehouseDTO>> {

    private final WarehouseDTOMapper warehouseDTOMapper;

    @Autowired
    public WarehouseDTOListMapper(WarehouseDTOMapper warehouseDTOMapper) {
        this.warehouseDTOMapper = warehouseDTOMapper;
    }

    @Override
    public List<WarehouseDTO> apply(List<Warehouse> warehouses) {
        return warehouseDTOMapper.applyList(warehouses);
    }
}
