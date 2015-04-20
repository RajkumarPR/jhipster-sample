package com.urbanbinge.repository;

import com.urbanbinge.domain.Event;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Event entity.
 */
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("select event from Event event where event.uId.login = ?#{principal.username}")
    List<Event> findAllForCurrentUser();

}
