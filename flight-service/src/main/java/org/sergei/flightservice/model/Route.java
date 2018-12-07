package org.sergei.flightservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_seq")
    @SequenceGenerator(name = "route_seq", sequenceName = "route_seq", allocationSize = 1)
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "place", nullable = false)
    private String place;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "reservation_id")
    private List<FlightReservation> flightReservationList = new LinkedList<>();
}
