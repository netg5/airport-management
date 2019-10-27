package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.FlyMode;
import org.sergei.manager.jpa.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface FlyModeRepository extends JpaRepository<FlyMode, Long> {

    /**
     * Find fly mode by code
     *
     * @param code by which should be found
     * @return Fly mode
     */
    @Query("SELECT fm FROM FlyMode fm WHERE fm.code = :code")
    Optional<FlyMode> findFlyModeByCode(@Param("code") String code);

    @Query("SELECT fm FROM FlyMode fm "
            + " JOIN fm.prices pr "
            + " JOIN pr.flyModes fmp ON fm.code = fmp.code "
            + " WHERE fm.code = :code "
            + " AND pr.currency = :currency")
    Optional<FlyMode> findFlyModeByCodeAndCurrency(@Param("code") String code,
                                                   @Param("currency") String currency);
}
