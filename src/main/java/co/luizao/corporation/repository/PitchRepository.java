package co.luizao.corporation.repository;

import co.luizao.corporation.domain.Pitch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pitch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PitchRepository extends JpaRepository<Pitch, Long> {}
