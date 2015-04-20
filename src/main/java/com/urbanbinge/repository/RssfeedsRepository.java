package com.urbanbinge.repository;

import com.urbanbinge.domain.Rssfeeds;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rssfeeds entity.
 */
public interface RssfeedsRepository extends JpaRepository<Rssfeeds,Long> {

}
