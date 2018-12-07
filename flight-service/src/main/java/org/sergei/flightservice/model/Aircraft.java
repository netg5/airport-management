package org.sergei.flightservice.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Aircraft", description = "Aircraft model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aircraft")
public class Aircraft extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_seq")
    @SequenceGenerator(name = "aircraft_seq", sequenceName = "aircraft_seq", allocationSize = 1)
    @Column(name = "aircraft_id")
    private Long aircraftId;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "aircraft_name", nullable = false)
    private String aircraftName;

    @Column(name = "weight", nullable = false)
    private Double aircraftWeight;

    @Column(name = "max_passengers", nullable = false)
    private Integer maxPassengers;
}
