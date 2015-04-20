package com.urbanbinge.repository;

import com.urbanbinge.domain.ExpertsArticle;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExpertsArticle entity.
 */
public interface ExpertsArticleRepository extends JpaRepository<ExpertsArticle,Long> {

}
