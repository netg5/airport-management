package org.sergei.reservation.jpa.model.mapper;

import org.sergei.reservation.jpa.model.Manufacturer;
import org.sergei.reservation.rest.dto.ManufacturerDTO;
import org.sergei.reservation.utils.IMapper;
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
