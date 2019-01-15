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

package org.sergei.frontendservice.controller;

import org.sergei.frontendservice.model.Customer;
import org.sergei.frontendservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

/**
 * @author Sergei Visotsky
 */
@Controller
public class MainController {

    private final CustomerService customerService;

    @Autowired
    public MainController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/customer")
    public String customerDataPage(Model model) {
        final Long customerId = 1L;
        ResponseEntity<Customer> customer = customerService.getCustomerByNumber(customerId);
        Customer customerResponseBody = customer.getBody();
        model.addAttribute("customerId", Objects.requireNonNull(customerResponseBody).getCustomerId());
        model.addAttribute("firstName", customerResponseBody.getFirstName());
        model.addAttribute("lastName", customerResponseBody.getLastName());
        model.addAttribute("age", customerResponseBody.getAge());
        return "customer";
    }
}
