package org.firms.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "regions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true) // не ноль, уник.
    private String shortName;

    @Column(nullable = false, unique = true)
    private String name;
}
