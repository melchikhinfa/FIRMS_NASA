package org.firms.backend.services;

import org.firms.backend.models.Region;
import org.firms.backend.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис обработки регионов
 */
@Service
public class RegionService {

    /**
     * Репозиторий регионов
     */
    private final RegionRepository repository;

    /**
     * Конструктор
     * @param repository
     */
    public RegionService(RegionRepository repository) {
        this.repository = repository;
    }

    /**
     * Получение всех регионов
     * @return Список регионов
     */
    public List<Region> getAllRegions(){
        return repository.findAll();

    }

    /**
     * Сохранить новый регион
     * @param region - Новый регион
     * @return - Регион
     */
    public Region save(Region region){
        repository.save(region);
        return region;
    }
}
