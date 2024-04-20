package org.firms.backend.jsonEntities.out.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO возвращаемого пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
}
