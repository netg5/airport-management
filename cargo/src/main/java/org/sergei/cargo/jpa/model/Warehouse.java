package org.sergei.cargo.jpa.model;

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
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouses")
public class Warehouse implements Serializable {

    private static final long serialVersionUID = -5323994921297876387L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "warehouses_id_seq")
    @SequenceGenerator(name = "warehouses_id_seq",
            sequenceName = "warehouses_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "warehouse_handling")
    private String warehouseHanding;

    @Column(name = "heavy_cargo")
    private Double heavyCargo;

    @Column(name = "dangerous")
    private String dangerous;

    @Column(name = "keep_cool_service")
    private String keepCoolService;

    @Column(name = "live_animals")
    private String liveAnimals;

    @Column(name = "valuable_cargo")
    private String valuableCargo;
}
