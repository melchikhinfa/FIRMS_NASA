package org.firms.backend.services;

import com.opencsv.exceptions.CsvValidationException;
import org.firms.backend.firmsRequests.FirmsRequests;
import org.firms.backend.jsonEntities.out.subscription.FireEntity;
import org.firms.backend.jsonEntities.out.subscription.GetStatusAPIKey;
import org.firms.backend.jsonEntities.out.subscription.SubscriptionEntity;
import org.firms.backend.models.Region;
import org.firms.backend.models.Subscription;
import org.firms.backend.models.User;
import org.firms.backend.repositories.RegionRepository;
import org.firms.backend.repositories.SubscriptionRepository;
import org.firms.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис обработки информации о подписках
 */
@Service
public class SubscriptionService {

    /**
     * Репозиторий подписки
     */
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Репозиторий регионов
     */
    private final RegionRepository regionRepository;

    /**
     * Репозиторий пользователя
     */
    private final UserRepository userRepository;

    /**
     * Конструктор сервиса
     * @param subscriptionRepository
     * @param regionRepository
     * @param userRepository
     */
    public SubscriptionService(SubscriptionRepository subscriptionRepository, RegionRepository regionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.regionRepository = regionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Список пожаров за сегодня по подписке
     * @param username - имя пользователя
     * @return Список FireEntity - пожаров
     * @throws CsvValidationException - ошибка обработки CSV
     * @throws IOException - отсутствует файл
     * @throws InterruptedException
     */
    public List<FireEntity> getFiresToday(String username) throws CsvValidationException, IOException, InterruptedException {
        return getFiresLastDays(username, "1");
    }

    /**
     * DEPRECATED. Пожары за несколько дней
     * @param username - имя пользователя
     * @param dateRange - список дней
     * @return Список FireEntity - пожаров
     * @throws CsvValidationException - ошибка обработки CSV
     * @throws IOException - отсутствует файл
     * @throws InterruptedException
     */
    public List<FireEntity> getFiresLastDays(String username, String dateRange) throws CsvValidationException, IOException, InterruptedException {
        User user = userRepository.findUserByUsername(username);
        List<SubscriptionEntity> subscriptionEntities = findAllByUser(username);
        List<FireEntity> entities = new ArrayList<>();
        for(SubscriptionEntity entity: subscriptionEntities){
            entities.addAll(FirmsRequests.getFiresFromCountry(user.getApiKey(), entity.getShortName(),dateRange));
        }
        return entities;

    }

    /**
     * Список подписок пользователя
     * @param username - имя пользователя
     * @return Список подписок пользователя
     */
    public List<SubscriptionEntity> findAllByUser(String username){
        User user = userRepository.findUserByUsername(username);
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserAndActive(user, true);
        List<SubscriptionEntity> subs = subscriptions
                .stream()
                .map(
                        subscription -> new SubscriptionEntity(
                                subscription.getId(),
                                subscription.getRegion().getShortName(),
                                subscription.getRegion().getName()))
                .toList();
        return subs;
    }

    /**
     * Добавление новой подписки пользователю
     * @param username - имя пользователя
     * @param regionId - идентификатор региона
     */
    public void addSubToUser(String username, UUID regionId){
        Region region = regionRepository.getReferenceById(regionId);
        User user = userRepository.findUserByUsername(username);
        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setRegion(region);
        sub.setActive(true);
        subscriptionRepository.save(sub);
    }

    /**
     * Удаление подписки у пользователя
     * @param subscriptionId - идентификатор подписки
     */
    public void deleteSubFromUser(UUID subscriptionId){
        Subscription subscription = subscriptionRepository.findById(subscriptionId).get();
        subscription.setActive(false);
        subscriptionRepository.save(subscription);
    }

    /**
     * Статус API ключа
     * @param username - имя пользователя
     * @return Сущность GetStatusAPIKey, с количеством транзакций пользователя
     * @throws IOException
     * @throws InterruptedException
     */
    public GetStatusAPIKey getStatusApiKey(String username) throws IOException, InterruptedException {
        User user = userRepository.findUserByUsername(username);
        return FirmsRequests.getStatusRequest(user.getApiKey());
    }

    /**
     * Получение списка регионов, на которые не подписан пользователь
     * @param username - имя пользователя
     * @return - Список регионов
     */
    public List<Region> getUserRegions(String username){
        User user = userRepository.findUserByUsername(username);
        List<Subscription> subs = subscriptionRepository.findAllByUserAndActive(user, true);

        List<Region> subRegions = subs.stream().map(Subscription::getRegion).toList();
        List<Region> regions = regionRepository.findAll();
        return regions.stream().filter(region -> {
            return !subRegions.contains(region);

        }).collect(Collectors.toList());
    }
}
