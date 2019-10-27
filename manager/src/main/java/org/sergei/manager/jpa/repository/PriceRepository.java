package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * !NB This JPA repository is experimental and is used for development purpose only.
 *
 * @author Sergei Visotsky
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT DISTINCT pr FROM Price pr "
            + " LEFT JOIN FETCH pr.flyModes "
            + " WHERE pr.currency = :currency")
    List<Price> findFlyModesPriceByCurrency(@Param("currency") String currency);
}
