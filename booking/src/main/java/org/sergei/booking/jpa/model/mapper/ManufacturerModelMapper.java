package org.sergei.booking.jpa.model.mapper;

import org.sergei.booking.jpa.model.Manufacturer;
import org.sergei.booking.rest.dto.ManufacturerDTO;
import org.sergei.booking.utils.IMapper;
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
