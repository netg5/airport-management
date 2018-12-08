package org.sergei.flightservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky, 2018
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "flight_reservation")
public class FlightReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_reservation_seq")
    @SequenceGenerator(name = "flight_reservation_seq", sequenceName = "flight_reservation_seq", allocationSize = 1)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    public FlightReservation(LocalDateTime reservationDate, Customer customer, Route route) {
        this.reservationDate = reservationDate;
        this.customer = customer;
        this.route = route;
    }
}
