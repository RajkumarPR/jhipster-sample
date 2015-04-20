package com.urbanbinge.repository;

import com.urbanbinge.domain.EventLocation;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventLocation entity.
 */
public interface EventLocationRepository extends JpaRepository<EventLocation,Long> {

}
