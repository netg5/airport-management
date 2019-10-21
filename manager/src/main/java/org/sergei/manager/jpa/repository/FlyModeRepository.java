package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.FlyMode;
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

    @Query(value =
            "SELECT * FROM fly_modes fm " +
            "   LEFT JOIN fly_modes_prices_relation fmpr " +
            "       ON fm.code = fmpr.fly_modes_code " +
            "   LEFT JOIN prices pr " +
            "       ON pr.code = fmpr.prices_code " +
            " WHERE fm.code = :code AND pr.currency = :currency", nativeQuery = true)
    Optional<FlyMode> findFlyModeByCodeAndCurrency(@Param("code") String code,
                                                   @Param("currency") String currency);
}
