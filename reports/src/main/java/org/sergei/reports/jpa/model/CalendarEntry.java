package org.sergei.reports.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;

/**
 * @author Sergei Visotsky
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendar_entry")
public class CalendarEntry implements Serializable {
    private static final long serialVersionUID = -458681317984562802L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "calendar_entry_id_seq")
    @SequenceGenerator(name = "calendar_entry_id_seq",
            sequenceName = "calendar_entry_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "day_number")
    private Integer dayNumber;

    @Column(name = "day_date")
    private Integer dayDate;

    @Column(name = "time_in_hours")
    private Time timeInHours;
}
