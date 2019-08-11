package org.sergei.manager.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO implements Serializable {
    private static final long serialVersionUID = -6509761031564127606L;
    private Long id;
    private String airportName;
    private String airportAddress;
    private String country;
    private String contactName;
    private String contactJob;
    private String email;
    private String phone;
}
