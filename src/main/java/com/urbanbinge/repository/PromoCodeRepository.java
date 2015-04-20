package com.urbanbinge.repository;

import com.urbanbinge.domain.PromoCode;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PromoCode entity.
 */
public interface PromoCodeRepository extends JpaRepository<PromoCode,Long> {

}
