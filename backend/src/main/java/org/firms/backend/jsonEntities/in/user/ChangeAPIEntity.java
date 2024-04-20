package org.firms.backend.jsonEntities.in.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO на смену API ключа
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAPIEntity {
    private String apiKey;
}
