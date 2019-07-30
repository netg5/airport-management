package org.sergei.manager.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PilotDTO implements Serializable {
    private static final long serialVersionUID = -2822152906257742754L;

    @JsonIgnore
    private Long id;
    private String licenseNumber;
    private String ssn;
    private String firstName;
    private String lastName;
    private String gender;
    private Double weight;
    private Date dateOfBirth;
    private String address;
    private String country;
    private String email;
    private String phone;
}
