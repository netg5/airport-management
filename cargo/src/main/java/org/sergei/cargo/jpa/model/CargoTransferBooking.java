package org.sergei.cargo.jpa.model;

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
@Table(name = "cargo_transfer_bookings")
public class CargoTransferBooking implements Serializable {
    private static final long serialVersionUID = -3098052535358401035L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cargo_transfer_booking_id_seq")
    @SequenceGenerator(name = "cargo_transfer_booking_id_seq",
            sequenceName = "cargo_transfer_booking_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_of_flying")
    private LocalDateTime dateOfFlying;

    @NotNull
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @NotNull
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @NotNull
    @Column(name = "hours_flying")
    private Integer hoursFlying;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinColumn(name = "cargo_id",
            referencedColumnName = "id")
    private Cargo cargo;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cargo_transfer_flight_id", referencedColumnName = "id",
            updatable = false, insertable = false)
    private CargoTransferFlight cargoTransferFlight;
}
