package org.sergei.reservation.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sergei Visotsky
 * @deprecated Should be removed after new business logic adoption
 */
@Getter
@Setter
@NoArgsConstructor
@Deprecated(forRemoval = true)
public class PassengerUpdateRequestDTO {
    private Long passengerId;
    private PassengerDTO customer;
}
