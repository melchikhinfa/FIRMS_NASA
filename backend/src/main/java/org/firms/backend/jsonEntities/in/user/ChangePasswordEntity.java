package org.firms.backend.jsonEntities.in.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO получаемая на смену пароля
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordEntity {
    private String oldPassword;
    private String newPassword;
}
