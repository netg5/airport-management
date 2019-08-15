package org.sergei.processor.jdbc.repository;

import org.sergei.processor.jdbc.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface PilotRepository extends JpaRepository<Pilot, Long> {

    @Query("SELECT p FROM Pilot p WHERE p.available = 1")
    List<Pilot> getAllAvailablePilots();

    @Query("UPDATE Pilot p SET p.available = 0 WHERE p.id = :pilotId")
    Pilot updatePilotStatusById(@Param("aircraftId") Long pilotId);

}
