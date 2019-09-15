package org.sergei.reports.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class OwnerDTO implements Serializable {
    private static final long serialVersionUID = -1365649840136103466L;

    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String country;
    private String email;
    private String phone;
}
