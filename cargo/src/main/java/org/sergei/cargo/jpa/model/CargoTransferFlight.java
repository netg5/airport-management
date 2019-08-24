package org.sergei.cargo.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargo_transfer_flights")
public class CargoTransferFlight implements Serializable {
    private static final long serialVersionUID = 1315287659704589144L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cargo_transfer_flight_id_seq")
    @SequenceGenerator(name = "cargo_transfer_flight_id_seq",
            sequenceName = "cargo_transfer_flight_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "place")
    private String place;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH})
    @JoinColumn(name = "aircraft_id",
            referencedColumnName = "id")
    private Aircraft aircraft;
}
