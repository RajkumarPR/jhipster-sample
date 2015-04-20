package com.urbanbinge.repository;

import com.urbanbinge.domain.GiftAnExperience;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GiftAnExperience entity.
 */
public interface GiftAnExperienceRepository extends JpaRepository<GiftAnExperience,Long> {

}
