package org.sergei.cargo.jpa.repository;

import org.sergei.cargo.jpa.model.SalesAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface SalesAgentRepository extends JpaRepository<SalesAgent, Long> {

    @Query("SELECT s FROM SalesAgent s WHERE s.agentCode = :code")
    SalesAgent findSalesAgentByCode(@Param("code") String agentCode);

}
