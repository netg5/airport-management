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
public class AircraftRequestDTO implements Serializable {
    private static final long serialVersionUID = 7739511086581969345L;
    private String modelNumber;
}
