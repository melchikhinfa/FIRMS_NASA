package org.firms.backend.controllers;


import com.opencsv.exceptions.CsvValidationException;
import org.firms.backend.jsonEntities.out.subscription.FireEntity;
import org.firms.backend.jsonEntities.out.subscription.GetStatusAPIKey;
import org.firms.backend.jsonEntities.out.subscription.SubscriptionEntity;
import org.firms.backend.models.Region;
import org.firms.backend.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * RestController по подпискам
 */
@RestController
@RequestMapping("/api/users/{username}/subscriptions")
public class SubscriptionController {

    /**
     * Сервис по подпискам
     */
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * GET запрос по получению списка подписок
     * @param username имя пользователя
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getUserSubscriptions(@PathVariable String username){

        List<SubscriptionEntity> subs = subscriptionService.findAllByUser(username);
        if(subs.isEmpty()){
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(subs, HttpStatus.OK);
        }

    }

    /**
     * GET запрос по получению списка пожаров по подпискам
     * @param username имя пользователя
     * @return
     */
    @GetMapping("/fires")
    public ResponseEntity<?> getFiresToday(@PathVariable String username) throws CsvValidationException, IOException, InterruptedException {
        List<FireEntity> entities = subscriptionService.getFiresToday(username);
        if(entities.isEmpty()){
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);

    }

    /**
     * GET запрос по получению списка подписок за несколько дней
     * @param username имя пользователя
     * @return
     */
    @GetMapping("/fires/{dayRange}")
    public ResponseEntity<?> getFiresLastNDays(@PathVariable String username, @PathVariable String dayRange) throws CsvValidationException, IOException, InterruptedException {
        List<FireEntity> entities = subscriptionService.getFiresLastDays(username, dayRange);
        if(entities.isEmpty()){
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    /**
     * POST запрос добавления подписки
     * @param username имя пользователя
     * @param regionId идентификатор региона
     * @return
     */
    @PostMapping("/regions/{regionId}")
    public ResponseEntity<?> addUserSubscriptions(@PathVariable String username, @PathVariable UUID regionId){

        subscriptionService.addSubToUser(username, regionId);
        return new ResponseEntity<>("", HttpStatus.CREATED);

    }

    /**
     * GET Запрос получения списка регионов без подписок у пользователя
     * @param username имя пользователя
     * @return
     */
    @GetMapping("/regions")
    public ResponseEntity<?> getUserRegionsFree(@PathVariable String username){

        List<Region> reg = subscriptionService.getUserRegions(username);
        return new ResponseEntity<>(reg, HttpStatus.OK);

    }

    /**
     * DELETE запрос на удаление подписки
     * @param username имя пользователя
     * @param subscriptionId идентификатор подписки
     * @return
     */
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<?> deleteOneSubscription(@PathVariable String username, @PathVariable UUID subscriptionId){
        subscriptionService.deleteSubFromUser(subscriptionId);
        return new ResponseEntity<>("", HttpStatus.OK);

    }

    /**
     * Получение статуса API ключа
     * @param username имя пользователя
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/apiStatus")
    public ResponseEntity<?> getStatusApiKey(@PathVariable String username) throws IOException, InterruptedException {
        GetStatusAPIKey api = subscriptionService.getStatusApiKey(username);
        return new ResponseEntity<>(api, HttpStatus.OK);
    }
}
