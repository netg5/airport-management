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

package org.sergei.tickets.jpa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Takes data from ticket_view located in MySQL server
 *
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "ticket_view")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1213518846387676066L;

    @Column(updatable = false, nullable = false)
    private Long passengerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Id
    @Column(name = "aircraft_id")
    private Long aircraftId;

    @Column(name = "date_of_flying")
    private LocalDateTime dateOfFlying;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "hours_flying")
    private Integer hoursFlying;

    @Column(name = "aircraft_name")
    private String aircraftName;

    @Column(name = "model_number")
    private String model_number;
}
