package org.sergei.cargo.jpa.repository;

import org.sergei.cargo.jpa.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query("SELECT w FROM Warehouse w WHERE w.warehouseCode = :code")
    Warehouse findWarehouseByCode(@Param("code") String warehouseCode);

}
