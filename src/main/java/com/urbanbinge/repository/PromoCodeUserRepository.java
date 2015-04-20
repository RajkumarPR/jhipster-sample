package com.urbanbinge.repository;

import com.urbanbinge.domain.PromoCodeUser;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PromoCodeUser entity.
 */
public interface PromoCodeUserRepository extends JpaRepository<PromoCodeUser,Long> {

}
