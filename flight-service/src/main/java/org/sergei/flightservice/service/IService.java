package org.sergei.flightservice.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface IService<E> {

    /**
     * Find one entity by ID
     *
     * @param aLong entity ID
     * @return entity found
     */
    E findOne(Long aLong);

    /**
     * Find all entities
     *
     * @return list of entities
     */
    List<E> findAll();

    /**
     * Find all entities paginated
     *
     * @param page number of page
     * @param size quantity of elements per page
     * @return page of entities
     */
    Page<E> findAllPaginated(int page, int size);

    /**
     * Save entity
     *
     * @param entityDTO entity data t o be saved
     * @return saved entity data
     */
    E save(E entityDTO);

    /**
     * Update all data fro entity
     *
     * @param aLong     entity ID to beupdated
     * @param entityDTO updated entity body
     * @return updated entity
     */
    E update(Long aLong, E entityDTO);

    /**
     * Patch one or multiple fields of one entity
     *
     * @param aLong  ID of the entity to be patched
     * @param params fields to be patched
     * @return patched entity
     */
    E patch(Long aLong, Map<String, Object> params);

    /**
     * Delete entity by ID
     *
     * @param aLong ID of the entity to be deleted
     * @return deleted entity body
     */
    E delete(Long aLong);
}
