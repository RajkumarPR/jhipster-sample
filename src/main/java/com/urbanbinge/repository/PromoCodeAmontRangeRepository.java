package com.urbanbinge.repository;

import com.urbanbinge.domain.PromoCodeAmontRange;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PromoCodeAmontRange entity.
 */
public interface PromoCodeAmontRangeRepository extends JpaRepository<PromoCodeAmontRange,Long> {

}
