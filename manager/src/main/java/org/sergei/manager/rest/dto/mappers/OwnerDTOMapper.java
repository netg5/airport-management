package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Owner;
import org.sergei.manager.rest.dto.OwnerDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class OwnerDTOMapper implements IMapper<Owner, OwnerDTO> {
    @Override
    public OwnerDTO apply(Owner owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .country(owner.getCountry())
                .email(owner.getEmail())
                .gender(owner.getGender())
                .phone(owner.getPhone())
                .build();
    }
}
