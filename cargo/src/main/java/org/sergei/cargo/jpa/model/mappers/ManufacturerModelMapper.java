package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.Manufacturer;
import org.sergei.cargo.rest.dto.ManufacturerDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ManufacturerModelMapper implements IMapper<ManufacturerDTO, Manufacturer> {

    @Override
    public Manufacturer apply(ManufacturerDTO manufacturerDTO) {
        return Manufacturer.builder()
                .id(manufacturerDTO.getId())
                .manufacturerName(manufacturerDTO.getManufacturerName())
                .manufacturerCode(manufacturerDTO.getManufacturerCode())
                .location(manufacturerDTO.getLocation())
                .build();
    }
}
