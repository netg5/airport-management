package org.sergei.booking.rest.dto.mappers;

import org.sergei.booking.jpa.model.FlyMode;
import org.sergei.booking.rest.dto.FlyModeDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class FlyModeDTOMapper implements IMapper<FlyMode, FlyModeDTO> {

    private final PriceDTOListMapper priceDTOListMapper;

    @Autowired
    public FlyModeDTOMapper(PriceDTOListMapper priceDTOListMapper) {
        this.priceDTOListMapper = priceDTOListMapper;
    }

    @Override
    public FlyModeDTO apply(FlyMode flyMode) {
        return FlyModeDTO.builder()
                .code(flyMode.getCode())
                .title(flyMode.getTitle())
                .description(flyMode.getDescription())
                .price(priceDTOListMapper.apply(flyMode.getPrice()))
                .build();
    }
}
