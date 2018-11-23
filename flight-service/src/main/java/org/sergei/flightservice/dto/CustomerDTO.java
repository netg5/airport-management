
package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.sergei.flightservice.controller.CustomerController;
import org.sergei.flightservice.controller.FlightReservationController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Customer", description = "Customer model")
public class CustomerDTO extends ResourceSupport {
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

        add(linkTo(CustomerController.class).withRel("customers"));
        add(linkTo(methodOn(FlightReservationController.class).getAllForCustomer(customerId)).withRel("reservations"));
        add(linkTo(methodOn(CustomerController.class).getCustomerById(customerId)).withSelfRel());
    }

    public Long getCustomerId() {
        return customerId;
    }

    @ApiModelProperty(hidden = true)
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
