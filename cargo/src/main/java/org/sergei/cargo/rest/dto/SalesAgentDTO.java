package org.sergei.cargo.rest.dto;

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
public class SalesAgentDTO implements Serializable {
    private static final long serialVersionUID = 5824168125775769325L;

    private String country;
    private String city;
    private String representative;
    private String email;
    private String phone;
}
