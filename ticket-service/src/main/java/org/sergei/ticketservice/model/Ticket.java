package org.sergei.ticketservice.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Ticket", description = "Ticket model")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ticket_view")
public class Ticket {

    @Id
    @Column(name = "first_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "place")
    private String place;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "price")
    private Float price;

    @Column(name = "aircraft_name")
    private String aircraftName;
}
