package org.sergei.reports.rest.dto;

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
public class FlightReportDTO implements Serializable {
    private static final long serialVersionUID = 278023319509442271L;

    private FlightDTO flightInfo;
}
