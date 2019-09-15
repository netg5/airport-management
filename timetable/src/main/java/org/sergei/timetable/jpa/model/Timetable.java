package org.sergei.timetable.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "timetable")
public class Timetable implements Serializable {
    private static final long serialVersionUID = 280735226109011333L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "timetable_id_seq")
    @SequenceGenerator(name = "timetable_id_seq",
            sequenceName = "timetable_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "scheduled_time")
    private Time scheduledTime;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "destination")
    private String destination;

    @Column(name = "hours_flying")
    private Integer hoursFlying;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
}
