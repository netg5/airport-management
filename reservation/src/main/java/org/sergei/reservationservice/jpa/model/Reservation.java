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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = -5534420368605880140L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "reservation_seq")
    @SequenceGenerator(name = "reservation_seq",
            sequenceName = "reservation_seq", allocationSize = 1)
    private Long id;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    public Reservation(LocalDateTime reservationDate, Customer customer, Route route) {
        this.reservationDate = reservationDate;
        this.customer = customer;
        this.route = route;
    }
}
