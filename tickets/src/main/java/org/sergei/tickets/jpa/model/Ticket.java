package org.sergei.tickets.jpa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "ticket_view")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1213518846387676066L;

    @Column(name = "passenger_id")
    private Long passengerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Id
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "date_of_flying")
    private LocalDateTime dateOfFlying;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "place")
    private String place;

    @Column(name = "title")
    private String title;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private String currency;
}