package com.urbanbinge.repository;

import com.urbanbinge.domain.Enquiry;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Enquiry entity.
 */
public interface EnquiryRepository extends JpaRepository<Enquiry,Long> {

}
