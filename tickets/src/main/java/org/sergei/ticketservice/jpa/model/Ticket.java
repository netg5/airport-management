/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.ticketservice.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Takes data from ticket_view located in MySQL server
 *
 * @author Sergei Visotsky
 */
@ApiModel(value = "Ticket", description = "Ticket model")
@Getter
@Setter
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
    private String firstName;

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
