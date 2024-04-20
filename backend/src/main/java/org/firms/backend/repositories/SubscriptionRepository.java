package org.firms.backend.repositories;

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
}
