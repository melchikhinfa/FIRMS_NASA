package org.firms.backend.controllers;


import org.firms.backend.models.Region;
import org.firms.backend.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST контроллер получения информации о регионах
 */
@RestController
@RequestMapping("/api/regions")
public class RegionController {
    /**
     * Сервис регионов
     */
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * Получение списка регионов
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Region>> getAllRegions(){
        return new ResponseEntity<>(regionService.getAllRegions(), HttpStatus.OK);
    }
}
