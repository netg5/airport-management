/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Customer", description = "Customer model")
public class CustomerDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;

    public CustomerDTO() {
    }

    public CustomerDTO(Long customerId, String firstName, String lastName, Integer age) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getCustomerId() {
        return customerId;
    }

    @ApiModelProperty(hidden = true, readOnly = true)
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
