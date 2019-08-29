package org.sergei.cargo.rest.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO implements Serializable {
    private static final long serialVersionUID = -5392093613160923673L;
    private Long id;
    private String manufacturerCode;
    private String manufacturerName;
    private String location;
}
