package org.firms.backend.repositories;

import org.firms.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий для генерации SQL Запросов по таблице USERS
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Найти пользователя по имени пользователя
     * @param username - имя пользователя
     * @return - Пользователь
     */
    User findUserByUsername(String username);
}
