package org.sergei.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 * Created on 11/27/2018
 *
 * <pre>
 *     Takes data from ticket_view located in MySQL server
 * </pre>
 */

@ApiModel(value = "Ticket", description = "Ticket model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "ticket_view")
public class Ticket extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Customer ID whose ticket is returned")
    @JsonIgnore
    @Column(name = "customer_id", updatable = false, nullable = false)
    private Long customerId;

    @ApiModelProperty("Customer first name whose ticket is returned")
    @Column(name = "first_name")
    private String fistName;

    @ApiModelProperty("Customer first name whose ticket is returned")
    @Column(name = "last_name")
    private String lastName;

    @ApiModelProperty("Route ID")
    @Id
    @Column(name = "route_id")
    private Long routeId;

    @ApiModelProperty("Place to fly")
    @Column(name = "place")
    private String place;

    @ApiModelProperty("Flight distance")
    @Column(name = "distance")
    private Double distance;

    @ApiModelProperty("Flight price")
    @Column(name = "price")
    private Float price;

    @ApiModelProperty("Aircraft name")
    @Column(name = "aircraft_name")
    private String aircraftName;
}
