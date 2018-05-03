package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.TVersion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TVersionRepository extends JpaRepository<TVersion, Long> {

}
