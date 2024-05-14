package org.firms.backend.repositories;

import org.firms.backend.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий по генерации SQL запросов к таблице regions
 */
public interface RegionRepository extends JpaRepository<Region, UUID> {
    //Region getReferenceById(UUID id);

    Region getReferenceByShortName(String shortName);

    Region getReferenceByName(String name);

    Region findByShortName(String rus);

    Region findAllByShortName(String shortName);
}
