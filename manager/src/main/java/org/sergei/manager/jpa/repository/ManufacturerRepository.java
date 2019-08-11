package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    @Query("SELECT m FROM Manufacturer m WHERE m.manufacturerCode = :code")
    Optional<Manufacturer> findByCode(@Param("code") String code);
}
