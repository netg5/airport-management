package org.sergei.reservation.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerUpdateRequestDTO {
    private Long customerId;
    private CustomerDTO customer;
}
