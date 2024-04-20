package org.firms.backend.jsonEntities.in.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO получаемыя из запроса на регистрацию
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpEntity {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String apiKey;
}
