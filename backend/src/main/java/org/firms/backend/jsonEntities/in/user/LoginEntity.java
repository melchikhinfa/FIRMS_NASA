package org.firms.backend.jsonEntities.in.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO получаемая на авторизацию
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginEntity {

    private String username;
    private String password;
}
