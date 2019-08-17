package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.FlyMode;
import org.sergei.reservation.rest.dto.FlyModeDTO;
import org.sergei.reservation.utils.IMapper;
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
