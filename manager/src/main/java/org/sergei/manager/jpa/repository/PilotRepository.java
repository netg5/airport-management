package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface PilotRepository extends JpaRepository<Pilot, Long> {
}
