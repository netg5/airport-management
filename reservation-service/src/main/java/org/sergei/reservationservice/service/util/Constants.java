/*
 * Copyright 2018-2019 Sergei Visotsky
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

package org.sergei.reservationservice.service.util;

/**
 * Constants that are used in service layer
 *
 * @author Sergei Visotsky
 */
public final class Constants {

    /**
     * Hide from the public access
     */
    private Constants() {
    }

    public static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";
    public static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";
    public static final String ROUTE_NOT_FOUND = "Route with this ID not found";
    public static final String RESERVATION_NOT_FOUND = "Reservation with this ID not found";
    public static final String RESERVATIONS_NOT_FOUND = "This customer has no reservations made";
}
