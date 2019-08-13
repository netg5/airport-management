package org.sergei.processor.jpa.repository;

import org.sergei.processor.jpa.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    @Query("SELECT a FROM Aircraft a WHERE a.available = 1")
    List<Aircraft> getAvailableAllAircrafts();

    @Query("UPDATE Aircraft a SET a.available = 0 WHERE a.id = :aircraftId")
    void updateAircraftStatusById(@Param("aircraftId") Long id);
}
