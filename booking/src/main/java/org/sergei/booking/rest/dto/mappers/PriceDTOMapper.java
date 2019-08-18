package org.sergei.booking.rest.dto.mappers;

import org.sergei.booking.jpa.model.Price;
import org.sergei.booking.rest.dto.PriceDTO;
import org.sergei.booking.utils.IMapper;
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
