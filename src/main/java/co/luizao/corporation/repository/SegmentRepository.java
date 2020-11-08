package co.luizao.corporation.repository;

import co.luizao.corporation.domain.Segment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Segment entity.
 */
@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
    @Query(
        value = "select distinct segment from Segment segment left join fetch segment.pitches left join fetch segment.timbres",
        countQuery = "select count(distinct segment) from Segment segment"
    )
    Page<Segment> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct segment from Segment segment left join fetch segment.pitches left join fetch segment.timbres")
    List<Segment> findAllWithEagerRelationships();

    @Query("select segment from Segment segment left join fetch segment.pitches left join fetch segment.timbres where segment.id =:id")
    Optional<Segment> findOneWithEagerRelationships(@Param("id") Long id);
}
