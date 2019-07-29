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

package org.sergei.reservation.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aircraft")
public class Aircraft implements Serializable {

    private static final long serialVersionUID = -155783393887085614L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "aircraft_id_seq")
    @SequenceGenerator(name = "aircraft_id_seq",
            sequenceName = "aircraft_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "manufacturer_code")
    private String manufacturerCode;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "aircraft_name", nullable = false)
    private String aircraftName;

    private Integer capacity;

    private Double weight;

    @Column(name = "exploitation_period")
    private Integer exploitationPeriod;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Owner owner;

}
