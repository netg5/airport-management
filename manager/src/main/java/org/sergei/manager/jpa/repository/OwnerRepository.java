package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
