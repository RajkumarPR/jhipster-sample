package com.urbanbinge.repository;

import com.urbanbinge.domain.ArticleComment;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ArticleComment entity.
 */
public interface ArticleCommentRepository extends JpaRepository<ArticleComment,Long> {

}
