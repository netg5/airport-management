package org.sergei.processor.jpa.model.mappers;

import org.sergei.processor.jpa.model.Manufacturer;
import org.sergei.processor.rest.dto.ManufacturerDTO;
import org.sergei.processor.utils.IMapper;
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
