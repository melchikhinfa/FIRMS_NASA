package org.firms.client.utils;

import org.firms.client.jsonEntities.out.user.UserEntity;

/**
 * Контекст текущего приложения
 */
public class Context {

    /**
     * Текущий пользователь
     */
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    public Context(){}

    /**
     * Текущий главный экран
     */
    private String mainScreenName;

    public String getMainScreenName() {
        return mainScreenName;
    }

    public void setMainScreenName(String mainScreenName) {
        this.mainScreenName = mainScreenName;
    }
}
