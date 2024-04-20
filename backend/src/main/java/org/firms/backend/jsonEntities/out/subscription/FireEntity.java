package org.firms.backend.jsonEntities.out.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO возвращаемых пожаров
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireEntity {
    private double latitude;
    private double longitude;
}
