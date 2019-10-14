package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Price;
import org.sergei.manager.rest.dto.PriceDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class PriceDTOListMapper implements IMapper<List<Price>, List<PriceDTO>> {

    private final PriceDTOMapper priceDTOMapper;

    @Autowired
    public PriceDTOListMapper(PriceDTOMapper priceDTOMapper) {
        this.priceDTOMapper = priceDTOMapper;
    }

    @Override
    public List<PriceDTO> apply(List<Price> price) {
        return priceDTOMapper.applyList(price);
    }
}
