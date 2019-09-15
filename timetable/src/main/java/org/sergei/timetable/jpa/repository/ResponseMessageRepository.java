package org.sergei.timetable.jpa.repository;

import org.sergei.timetable.jpa.model.ResponseMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ResponseMessageRepository extends JpaRepository<ResponseMessage, Long> {

    /**
     * Select response message by code
     *
     * @param code message code
     * @return Reponse message wrapped into the collection
     */
    @Query("SELECT rm FROM ResponseMessage rm WHERE rm.code = :code")
    List<ResponseMessage> findResponseMessageByCode(@Param("code") String code);
}
