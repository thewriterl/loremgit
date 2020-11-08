package co.luizao.corporation.repository;

import co.luizao.corporation.domain.AudioFeatures;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AudioFeatures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioFeaturesRepository extends JpaRepository<AudioFeatures, Long> {}
