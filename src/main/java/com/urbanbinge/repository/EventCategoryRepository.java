package com.urbanbinge.repository;

import com.urbanbinge.domain.EventCategory;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventCategory entity.
 */
public interface EventCategoryRepository extends JpaRepository<EventCategory,Long> {

}
