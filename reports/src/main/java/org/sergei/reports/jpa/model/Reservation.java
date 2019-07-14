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

package org.sergei.reports.jpa.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Reservation", description = "Reservation made")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
@Immutable
public class Reservation extends ResourceSupport {

    @ApiModelProperty("Reservation ID")
    @Id
    @Column(name = "reservation_id")
    private Long reservationId;

    @ApiModelProperty("Date when reservation was made")
    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @ApiModelProperty("Customer ID who made reservation")
    @Column(name = "customer_id")
    private Long customerId;

    @ApiModelProperty("Destination route I")
    @Column(name = "route_id")
    private Long routeId;
}
