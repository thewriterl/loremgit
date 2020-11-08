package co.luizao.corporation.repository;

import co.luizao.corporation.domain.Timbre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Timbre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimbreRepository extends JpaRepository<Timbre, Long> {}
