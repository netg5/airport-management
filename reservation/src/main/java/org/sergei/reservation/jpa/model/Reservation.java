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

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@Setter // Should be removed after business logic gonna be ready
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = -5534420368605880140L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "reservation_id_seq")
    @SequenceGenerator(name = "reservation_id_seq",
            sequenceName = "reservation_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_of_flying")
    private LocalDateTime dateOfFlying;

    @NotNull
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @NotNull
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @NotNull
    @Column(name = "hours_flying")
    private Integer hoursFlying;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(
            name = "passenger_id",
            referencedColumnName = "id"
    )
    private Passenger passenger;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "route_id",
            referencedColumnName = "id"
    )
    private Route route;
}
