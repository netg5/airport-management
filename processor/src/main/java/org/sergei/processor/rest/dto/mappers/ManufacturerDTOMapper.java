package org.sergei.processor.rest.dto.mappers;

import org.sergei.processor.jpa.model.Manufacturer;
import org.sergei.processor.rest.dto.ManufacturerDTO;
import org.sergei.processor.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class ManufacturerDTOMapper implements IMapper<Manufacturer, ManufacturerDTO> {
    @Override
    public ManufacturerDTO apply(Manufacturer manufacturer) {
        return ManufacturerDTO.builder()
                .id(manufacturer.getId())
                .location(manufacturer.getLocation())
                .manufacturerCode(manufacturer.getManufacturerCode())
                .manufacturerName(manufacturer.getManufacturerName())
                .build();
    }
}
