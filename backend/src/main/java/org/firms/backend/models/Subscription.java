package org.firms.backend.models;

import jakarta.persistence.*; // Java Persistence API (JPA)
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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

    @Min(-90)  // Минимальное значение для широты
    @Max(90)   // Максимальное значение для широты
    private double latitude = 55.739042329022396;  // Широта, инициализация по умолчанию

    @Min(-180) // Минимальное значение для долготы
    @Max(180)  // Максимальное значение для долготы
    private double longitude = 37.487034455260954; // Долгота, инициализация по умолчанию
}
