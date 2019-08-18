package org.sergei.booking.jpa.model.mapper;

import org.sergei.booking.jpa.model.FlyMode;
import org.sergei.booking.rest.dto.FlyModeDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class FlyModeModelMapper implements IMapper<FlyModeDTO, FlyMode> {

    private final PriceListModelMapper priceListModelMapper;

    @Autowired
    public FlyModeModelMapper(PriceListModelMapper priceListModelMapper) {
        this.priceListModelMapper = priceListModelMapper;
    }

    @Override
    public FlyMode apply(FlyModeDTO flyModeDTO) {
        return FlyMode.builder()
                .code(flyModeDTO.getCode())
                .title(flyModeDTO.getTitle())
                .description(flyModeDTO.getDescription())
                .price(priceListModelMapper.apply(flyModeDTO.getPrice()))
                .build();
    }
}
