package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.TInstance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TInstanceRepository extends JpaRepository<TInstance, Long> {
    @Query("select distinct t_instance from TInstance t_instance left join fetch t_instance.templates")
    List<TInstance> findAllWithEagerRelationships();

    @Query("select t_instance from TInstance t_instance left join fetch t_instance.templates where t_instance.id =:id")
    TInstance findOneWithEagerRelationships(@Param("id") Long id);

}
