package com.urbanbinge.repository;

import com.urbanbinge.domain.EventTicket;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventTicket entity.
 */
public interface EventTicketRepository extends JpaRepository<EventTicket,Long> {

}
