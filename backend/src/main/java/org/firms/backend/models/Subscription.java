package org.firms.backend.models;


import jakarta.persistence.*; // Java Persistence API (JPA)
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity // сущность JPA
@Table(name = "subscriptions") // имя таблицы
@AllArgsConstructor // генерирует конструктор с параметрами для всех полей класса (генерация Lombok)
@NoArgsConstructor // конструктор без параметров
@Data // генерирует геттеры и сеттеры для всех полей класса
public class Subscription {

    @Id
    @GeneratedValue
    private UUID id; // первичный ключ uuid (автогенер.)

    @ManyToOne
    private User user;

    @ManyToOne
    private Region region;

    private boolean active; // индикатор активности подписки
}
