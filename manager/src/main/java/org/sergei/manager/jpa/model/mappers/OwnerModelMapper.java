package org.sergei.manager.jpa.model.mappers;

import org.sergei.manager.jpa.model.Owner;
import org.sergei.manager.rest.dto.OwnerDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class OwnerModelMapper implements IMapper<OwnerDTO, Owner> {
    @Override
    public Owner apply(OwnerDTO ownerDTO) {
        return Owner.builder()
                .firstName(ownerDTO.getFirstName())
                .lastName(ownerDTO.getLastName())
                .gender(ownerDTO.getGender())
                .address(ownerDTO.getAddress())
                .country(ownerDTO.getCountry())
                .email(ownerDTO.getEmail())
                .phone(ownerDTO.getPhone())
                .build();
    }
}
