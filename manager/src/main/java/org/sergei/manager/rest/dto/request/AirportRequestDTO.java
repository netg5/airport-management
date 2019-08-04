package org.sergei.manager.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequestDTO implements Serializable {
    private static final long serialVersionUID = 3199495582125199745L;
    private String airportName;
}
