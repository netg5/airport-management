package org.sergei.reservation.jpa.model.mapper;

import org.sergei.reservation.jpa.model.Price;
import org.sergei.reservation.rest.dto.PriceDTO;
import org.sergei.reservation.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class PriceListModelMapper implements IMapper<List<PriceDTO>, List<Price>> {

    private final PriceModelMapper priceModelMapper;

    @Autowired
    public PriceListModelMapper(PriceModelMapper priceModelMapper) {
        this.priceModelMapper = priceModelMapper;
    }

    @Override
    public List<Price> apply(List<PriceDTO> priceDTOS) {
        return priceModelMapper.applyList(priceDTOS);
    }
}
