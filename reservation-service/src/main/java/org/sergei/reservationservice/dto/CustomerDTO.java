package org.sergei.reservationservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Customer", description = "Customer model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO extends ResourceSupport {

    @ApiModelProperty("Customer ID")
    private Long customerId;

    @ApiModelProperty("Customer first name")
    private String firstName;

    @ApiModelProperty("Customer last name")
    private String lastName;

    @ApiModelProperty("Customer age")
    private Integer age;
}