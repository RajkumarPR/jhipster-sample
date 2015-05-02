package com.urbanbinge.repository;

import com.urbanbinge.domain.EventCategorMap;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventCategorMap entity.
 */
public interface EventCategorMapRepository extends JpaRepository<EventCategorMap,Long> {

}
