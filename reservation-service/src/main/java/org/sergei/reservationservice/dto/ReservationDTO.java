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

package org.sergei.reservationservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Reservation", description = "Flight reservation meta data model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO extends ResourceSupport {

    @ApiModelProperty("Reservation ID")
    private Long reservationId;

    @ApiModelProperty("Customer ID who made reservation")
    private Long customerId;

    @ApiModelProperty("Reserved route ID")
    private Long routeId;

    @ApiModelProperty("Flight reservation date")
    private LocalDateTime reservationDate;
}
