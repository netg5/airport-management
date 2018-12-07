package org.sergei.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 * @since 11/27/2018
 *
 * <pre>
 *     Takes data from ticket_view located in MySQL server
 * </pre>
 */
@ApiModel(value = "Ticket", description = "Ticket model")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "ticket_view")
public class Ticket extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Column(name = "customer_id", updatable = false, nullable = false)
    private Long customerId;

    @Column(name = "first_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Id
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
