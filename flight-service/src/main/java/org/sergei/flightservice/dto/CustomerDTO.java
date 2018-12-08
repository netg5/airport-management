package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Customer", description = "Customer model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO extends ResourceSupport {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;
}