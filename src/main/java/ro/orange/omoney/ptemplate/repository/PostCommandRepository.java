package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.PostCommand;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PostCommand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostCommandRepository extends JpaRepository<PostCommand, Long> {

}
