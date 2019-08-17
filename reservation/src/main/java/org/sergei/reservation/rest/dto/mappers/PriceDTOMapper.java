package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Price;
import org.sergei.reservation.rest.dto.PriceDTO;
import org.sergei.reservation.utils.IMapper;
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
