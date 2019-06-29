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

package org.sergei.reportservice.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.sergei.reportservice.jpa.model.Reservation;
import org.springframework.hateoas.ResourceSupport;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "CustomerReport", description = "Customer report model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportDTO extends ResourceSupport {

    @ApiModelProperty("Customer ID who made reservation")
    private Long customerId;

    @ApiModelProperty("First name of customer who made reservation")
    private String firstName;

    @ApiModelProperty("Last name of the customer who made reservation")
    private String lastName;

    @ApiModelProperty("Collection of reservations made by customer")
    private List<Reservation> reservations = new LinkedList<>();
}
