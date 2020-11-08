package co.luizao.corporation.repository;

import co.luizao.corporation.domain.AudioAnalysis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AudioAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioAnalysisRepository extends JpaRepository<AudioAnalysis, Long> {}
