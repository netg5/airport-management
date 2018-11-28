package org.sergei.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sergei Visotsky, 2018
 *
 * <pre>
 *     Takes date from ticket_view located in MySQL server
 * </pre>
 */
@ApiModel(value = "Ticket", description = "Ticket model")
@Entity
@Table(name = "ticket_view")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @JsonIgnore
    @Id
    @Column(name = "customer_id")
    private Long customerId;

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
