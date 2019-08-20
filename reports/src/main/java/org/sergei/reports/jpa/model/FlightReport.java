package org.sergei.reports.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Entity
@Table(name = "flight_report")
public class FlightReport {

    private Long flightId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double distance;
    private String place;
    private Double price;

}
