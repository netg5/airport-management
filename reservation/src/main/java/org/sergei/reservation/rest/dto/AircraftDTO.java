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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDTO implements Serializable {
    private static final long serialVersionUID = -8398761845885572454L;
    private Long aircraftId;
    private String registrationNumber;
    private String modelNumber;
    private String aircraftName;
    private Integer capacity;
    private Double weight;
    private Integer exploitationPeriod;
    @JsonIgnoreProperties(value = "aircraft")
    private HangarDTO hangar;
    private ManufacturerDTO manufacturer;
}