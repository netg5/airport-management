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

package org.sergei.reservation.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(
        value = "Reservation",
        description = "Flight reservation meta data model"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO implements Serializable {

    private static final long serialVersionUID = -2808793016869498675L;

    @ApiModelProperty("Reservation ID")
    private Long reservationId;

    @ApiModelProperty("Customer ID who made reservation")
    private Long customerId;

    @ApiModelProperty("Flight reservation date")
    private LocalDateTime reservationDate;

    @ApiModelProperty("Route which is reserved")
    private RouteResponseDTO routes;
}