package org.sergei.orchestration.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO implements Serializable {
    private static final long serialVersionUID = 8935033626830093932L;

    private String code;
    private Double amount;
    private String currency;
}
