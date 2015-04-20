package com.urbanbinge.repository;

import com.urbanbinge.domain.Cities;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cities entity.
 */
public interface CitiesRepository extends JpaRepository<Cities,Long> {

}
