package org.sergei.reservation.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftRequestDTO implements Serializable {
    private static final long serialVersionUID = -1870099730824332667L;
    private Long aircraftId;
}
