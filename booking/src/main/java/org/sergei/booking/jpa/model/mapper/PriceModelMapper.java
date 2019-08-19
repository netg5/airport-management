package org.sergei.booking.jpa.model.mapper;

import org.sergei.booking.jpa.model.Price;
import org.sergei.booking.rest.dto.PriceDTO;
import org.sergei.booking.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PriceModelMapper implements IMapper<PriceDTO, Price> {
    @Override
    public Price apply(PriceDTO priceDTO) {
        return Price.builder()
                .code(priceDTO.getCode())
                .amount(priceDTO.getAmount())
                .currency(priceDTO.getCurrency())
                .build();
    }
}
