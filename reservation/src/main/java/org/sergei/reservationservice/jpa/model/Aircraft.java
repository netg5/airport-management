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

package org.sergei.reservationservice.jpa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "aircraft")
public class Aircraft implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_seq")
    @SequenceGenerator(name = "aircraft_seq", sequenceName = "aircraft_seq", allocationSize = 1)
    @Column(name = "aircraft_id")
    private Long aircraftId;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "aircraft_name", nullable = false)
    private String aircraftName;

    @Column(name = "weight", nullable = false)
    private Double aircraftWeight;

    @Column(name = "max_passengers", nullable = false)
    private Integer maxPassengers;

    public Aircraft(String model, String aircraftName, Double aircraftWeight, Integer maxPassengers) {
        this.model = model;
        this.aircraftName = aircraftName;
        this.aircraftWeight = aircraftWeight;
        this.maxPassengers = maxPassengers;
    }
}
