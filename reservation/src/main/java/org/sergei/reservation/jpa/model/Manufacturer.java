package org.sergei.reservation.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manufacturer")
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 2914359527061574119L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "manufacturer_id_seq")
    @SequenceGenerator(name = "manufacturer_id_seq",
            sequenceName = "manufacturer_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "manufacturer_code")
    private String manufacturerCode;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Column(name = "location")
    private String location;

}
