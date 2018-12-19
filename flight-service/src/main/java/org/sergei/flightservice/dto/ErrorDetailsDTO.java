package org.sergei.flightservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Sergei Visotsky
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailsDTO {
    private Date timestamp;
    private String message;
    private String details;
}
