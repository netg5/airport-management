package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Customer", description = "Customer model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO extends ResourceSupport {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;
}