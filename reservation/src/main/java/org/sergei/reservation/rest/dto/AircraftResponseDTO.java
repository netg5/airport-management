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

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Aircraft", description = "Aircraft model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftResponseDTO implements Serializable {

    private static final long serialVersionUID = -8398761845885572454L;

    @ApiModelProperty("Aircraft ID")
    private Long aircraftId;

    @ApiModelProperty("Aircraft model")
    private String model;

    @ApiModelProperty("Aircraft name")
    private String aircraftName;

    @ApiModelProperty("Aircraft name")
    private Double aircraftWeight;

    @ApiModelProperty("Maximum passengers in aircraft")
    private Integer maxPassengers;
}