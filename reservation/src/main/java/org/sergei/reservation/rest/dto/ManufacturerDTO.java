package org.sergei.reservation.rest.dto;

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
public class ManufacturerDTO implements Serializable {
    private static final long serialVersionUID = -5392093613160923673L;
    private Long id;
    private String manufacturerCode;
    private String manufacturerName;
    private String location;
}
