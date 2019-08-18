package org.sergei.manager.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@ToString
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
    private Boolean available;
}
