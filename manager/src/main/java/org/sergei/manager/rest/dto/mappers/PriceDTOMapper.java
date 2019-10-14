package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Price;
import org.sergei.manager.rest.dto.PriceDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PriceDTOMapper implements IMapper<Price, PriceDTO> {
    @Override
    public PriceDTO apply(Price price) {
        return PriceDTO.builder()
                .code(price.getCode())
                .amount(price.getAmount())
                .currency(price.getCurrency())
                .build();
    }
}
