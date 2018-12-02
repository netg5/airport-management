package org.sergei.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Sergei Visotsky, 2018
 *
 * <pre>
 *     Takes date from ticket_view located in MySQL server
 * </pre>
 */
@ApiModel(value = "Ticket", description = "Ticket model")
@Data
@NoArgsConstructor
@AllArgsConstructor
/*@SqlResultSetMapping(
        name = "ticketDetailsView",
        entities = @EntityResult(
                entityClass = Ticket.class,
                fields = {
                        @FieldResult(name = "fistName", column = "first_name"),
                        @FieldResult(name = "lastName", column = "last_name"),
                        @FieldResult(name = "routeId", column = "route_id"),
                        @FieldResult(name = "place", column = "place"),
                        @FieldResult(name = "distance", column = "distance"),
                        @FieldResult(name = "price", column = "price"),
                        @FieldResult(name = "aircraftName", column = "aircraft_name")
                }
        )
)
@NamedNativeQuery(
        name = "findAllByCustomerId",
        query = "SELECT * FROM ticket_view t WHERE t.customer_id = :customerId",
        resultSetMapping = "ticketDetailsView"
)*/
@Entity
@Immutable
@Table(name = "ticket_view")
public class Ticket extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@Id
    private Long ticketId;*/

    @JsonIgnore
    @Id
    @Column(name = "customer_id", updatable = false, nullable = false)
    private Long customerId;

    @Column(name = "first_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "place")
    private String place;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "price")
    private Float price;

    @Column(name = "aircraft_name")
    private String aircraftName;
}
