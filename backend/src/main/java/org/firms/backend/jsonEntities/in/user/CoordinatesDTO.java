package org.firms.backend.jsonEntities.in.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO получаемая на ввод координат пользователем
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesDTO {
    private double latitude;
    private double longitude;

}
