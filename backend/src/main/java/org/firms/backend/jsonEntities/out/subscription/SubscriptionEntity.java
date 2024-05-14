package org.firms.backend.jsonEntities.out.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO возвращаемых подписок
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEntity {
    private UUID id;
    private String shortName;
    private String regionName;
    private double latitude;
    private double longitude;
}
