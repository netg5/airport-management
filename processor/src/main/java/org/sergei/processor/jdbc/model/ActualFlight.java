package org.sergei.processor.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actual_flight")
public class ActualFlight implements Serializable {

    private static final long serialVersionUID = -6683080732212544539L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "actual_flight_id_seq")
    @SequenceGenerator(name = "actual_flight_id_seq",
            sequenceName = "actual_flight_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long actualFlightId;

    @Column(name = "date_of_flying")
    private LocalDateTime dateOfFlying;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "hours_flying")
    private Integer hoursFlying;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "aircraft_id",
            referencedColumnName = "id"
    )
    private Long aircraftId;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "pilot_id",
            referencedColumnName = "id"
    )
    private Long pilotId;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "route_id",
            referencedColumnName = "id"
    )
    private Long routeId;
}
