package org.firms.backend.repositories;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.firms.backend.models.Region;
import org.firms.backend.models.Subscription;
import org.firms.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий по генерации SQL Запросов по таблице SUBSCRIPTIONS
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    /**
     * Найти все подписки пользователя
     * @param user - пользователь
     * @return - Список подписок пользователя
     */
    List<Subscription> findAllByUser(User user);

    /**
     * Найти все подписки по пользователю и флагу isActive
     * @param user - Пользователь
     * @param isActive - активна запись или нет
     * @return - Список подписок
     */
    List<Subscription> findAllByUserAndActive(User user, boolean isActive);

    /**
     * Найти все подписки по пользователю и региону
     * @param user - Пользователь
     * @param region - Регион
     * @return - Список подписок
     */
    List<Subscription> findAllByUserAndRegion(User user, Region region);

    /**
     * Найти все подписки по пользователю и широте/долготе
     * @param user - Пользователь
     * @param latitude - широта
     * @param longitude - долгота
     * @return - Список подписок
     */
    List<Subscription> findAllByUserAndLatitudeAndLongitude(User user, @Min(-90) @Max(90) double latitude, @Min(-180) @Max(180) double longitude);

    List<Subscription> findByUserAndRegionAndActive(User user, Region region, boolean isActive);

    /**
     * Найти все подписки по пользователю и айди подписки
     * @param user - Пользователь
     * @param subscriptionId - айди подписки
     * @return
     */
    List<Subscription> findAllByUserAndId(User user, UUID subscriptionId);
}
