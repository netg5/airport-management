package org.sergei.timetable.jpa.repository;

import org.sergei.timetable.jpa.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
}
