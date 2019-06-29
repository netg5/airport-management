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

package org.sergei.reservationservice.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Aircraft", description = "Aircraft model")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDTO extends ResourceSupport {

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