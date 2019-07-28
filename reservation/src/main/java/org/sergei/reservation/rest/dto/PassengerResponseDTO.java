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
@ApiModel(value = "Passenger", description = "Passenger model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponseDTO implements Serializable {

    private static final long serialVersionUID = 1601177547641029786L;

    @ApiModelProperty("Passenger ID")
    private Long passengerId;

    @ApiModelProperty("Passenger first name")
    private String firstName;

    @ApiModelProperty("Passenger last name")
    private String lastName;

    @ApiModelProperty("Passenger age")
    private Integer age;

    private String gender;

    private Integer phone;
}