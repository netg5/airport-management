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

package org.sergei.booking.jpa.repository;

import org.sergei.booking.jpa.model.Booking;
import org.sergei.booking.jpa.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Find booking for passenger by ID
     *
     * @param passengerId whose booking should be found
     * @param bookingId   booking which should be found
     * @return list of bookings
     */
    @Query("SELECT r FROM Booking r WHERE r.passenger.id = :passengerId AND r.id = :bookingId")
    Optional<Booking> findOneForPassenger(@Param("passengerId") Long passengerId,
                                          @Param("bookingId") Long bookingId);

    /**
     * Find all bookings by passenger ID
     *
     * @param passengerId whose booking should be found
     * @return list of bookings
     */
    @Query("SELECT r FROM Booking r WHERE r.passenger.id = :passengerId")
    List<Booking> findAllForPassenger(@Param("passengerId") Long passengerId);

    /**
     * Find all bookings by passenger ID paginated
     *
     * @param passengerId whose bookings should be found
     * @param pageable    page size and record quantity per page
     * @return list of bookings
     */
    @Query("SELECT r FROM Booking r WHERE r.passenger.id = :passengerId")
    Page<Booking> findAllForPassengerPaginated(@Param("passengerId") Long passengerId,
                                               Pageable pageable);

    /**
     * Method to delete booking by passenger and booking found
     *
     * @param passenger passenger found and given as a parameter
     * @param booking   booking found and given as a parameter
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Booking r WHERE r.passenger = ?1 AND r = ?2")
    void discardByPassengerIdAndBookingId(Passenger passenger, Booking booking);
}
