package org.sergei.tickets.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Ticket", description = "Ticket model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO implements Serializable {

    private static final long serialVersionUID = 4746464867827649037L;

    @ApiModelProperty("Customer ID whose ticket is returned")
    @JsonIgnore
    private Long customerId;

    @ApiModelProperty("Customer first name whose ticket is returned")
    private String firstName;

    @ApiModelProperty("Customer first name whose ticket is returned")
    private String lastName;

    @ApiModelProperty("Route ID")
    private Long routeId;

    @ApiModelProperty("Place to fly")
    private String place;

    @ApiModelProperty("Flight distance")
    private Double distance;

    @ApiModelProperty("Flight price")
    private Float price;

    @ApiModelProperty("Aircraft name")
    private String aircraftName;
}
