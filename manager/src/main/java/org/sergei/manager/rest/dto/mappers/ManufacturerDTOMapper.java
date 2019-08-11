package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Manufacturer;
import org.sergei.manager.rest.dto.ManufacturerDTO;
import org.sergei.manager.utils.IMapper;
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
