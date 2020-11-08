package co.luizao.corporation.repository;

import co.luizao.corporation.domain.Section;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Section entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {}
