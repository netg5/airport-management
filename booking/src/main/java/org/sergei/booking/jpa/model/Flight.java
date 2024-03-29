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

package org.sergei.booking.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight")
public class Flight implements Serializable {

    private static final long serialVersionUID = -5365925665500006894L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "flight_id_seq")
    @SequenceGenerator(name = "flight_id_seq",
            sequenceName = "flight_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "place")
    private String place;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH})
    @JoinColumn(name = "aircraft_id",
            referencedColumnName = "id")
    private Aircraft aircraft;
}
