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
@Table(name = "cargo")
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1393684547525933990L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cargo_id_seq")
    @SequenceGenerator(name = "cargo_id_seq",
            sequenceName = "cargo_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "internal_volume")
    private Double internalVolume;

    @Column(name = "length")
    private Double length;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Double height;

    @Column(name = "max_gross_weight")
    private Integer maxGrossWeight;

    @Column(name = "tare_weight")
    private Integer tareWeight;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE})
    @JoinColumn(name = "warehouse_id",
            referencedColumnName = "id")
    private Warehouse warehouse;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE})
    @JoinColumn(name = "sales_agent_id",
            referencedColumnName = "id")
    private SalesAgent salesAgent;
}
