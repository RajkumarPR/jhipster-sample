package com.urbanbinge.repository;

import com.urbanbinge.domain.ArticleImage;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ArticleImage entity.
 */
public interface ArticleImageRepository extends JpaRepository<ArticleImage,Long> {

}
