package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.Hangar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface HangarRepository extends JpaRepository<Hangar, Long> {

    @Query("SELECT h FROM Hangar h WHERE h.capacity = :capacity")
    List<Hangar> findHangarsByCapacity(@Param("capacity") Integer capacity);

    @Query("SELECT " +
            " h " +
            " FROM Hangar h" +
            " LEFT JOIN " +
            " h.aircrafts air ON h.id = air.id" +
            " WHERE h.capacity = :capacity"

    )
    Page<Hangar> findHangarsByCapacityWithAircrafts(@Param("capacity") Integer capacity, Pageable var1);
}
