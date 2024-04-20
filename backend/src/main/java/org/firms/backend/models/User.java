package org.firms.backend.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Класс - сущность для ORM к таблице USERS
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Имя пользователя
     */
    @Column(nullable = false, unique = true)
    private String username;
    /**
     * Пароль
     */
    @Column(nullable = false)
    private String password;
    /**
     * Имя
     */
    @Column(nullable = false)
    private String firstName;
    /**
     * Фамилия
     */
    @Column(nullable = false)
    private String lastName;
    /**
     * Отчество
     */
    private String middleName;
    /**
     * API ключ к FIRMS
     */
    private String apiKey;
}
