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

package org.sergei.booking.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sergei.booking.rest.dto.BookingDTO;
import org.sergei.booking.rest.dto.request.BookingRequestDTO;
import org.sergei.booking.rest.dto.response.ResponseDTO;
import org.sergei.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"bookingCrudOperations"})
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/getAllBookings")
    public ResponseEntity<ResponseDTO<BookingDTO>> getAllBookings() {
        return bookingService.findAll();
    }

    @GetMapping(value = "/getAllBookingsForPassenger/{passengerId}")
    public ResponseEntity<ResponseDTO<BookingDTO>> getAllBookingsForPassenger(@PathVariable("passengerId") Long passengerId) {
        return bookingService.findAllForPassenger(passengerId);
    }

    @GetMapping(value = "/getOneBookingForPassenger/{passengerId}")
    public ResponseEntity<ResponseDTO<BookingDTO>> getOneBookingForPassenger(@PathVariable("passengerId") Long passengerId,
                                                                             @RequestParam("bookingId") Long bookingId) {
        return bookingService.findOneForPassenger(passengerId, bookingId);
    }

    @PostMapping(value = "/makeBooking")
    public ResponseEntity<ResponseDTO<BookingDTO>> makeBooking(@RequestBody BookingRequestDTO request) {
        return bookingService.saveBooking(request);
    }

    @ApiOperation("Discard booking")
    @DeleteMapping("/discardBooking/{passengerId}")
    public ResponseEntity<ResponseDTO<BookingDTO>> discardBooking(@PathVariable("passengerId") Long passengerId,
                                                                  @RequestParam("bookingId") Long bookingId) {
        return bookingService.discardBooking(passengerId, bookingId);
    }
}
