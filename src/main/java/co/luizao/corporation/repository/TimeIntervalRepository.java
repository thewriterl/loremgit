package co.luizao.corporation.repository;

import co.luizao.corporation.domain.TimeInterval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TimeInterval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeIntervalRepository extends JpaRepository<TimeInterval, Long> {}
