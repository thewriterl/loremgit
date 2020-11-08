package co.luizao.corporation.repository;

import co.luizao.corporation.domain.ExternalURL;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExternalURL entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalURLRepository extends JpaRepository<ExternalURL, Long> {}
