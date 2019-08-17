package org.sergei.reservation.jpa.model.mapper;

import org.sergei.reservation.jpa.model.FlyMode;
import org.sergei.reservation.rest.dto.FlyModeDTO;
import org.sergei.reservation.utils.IMapper;
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
