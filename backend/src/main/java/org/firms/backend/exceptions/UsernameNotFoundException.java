package org.firms.backend.exceptions;

/**
 * Пользователь с таким именем не найден
 */
public class UsernameNotFoundException extends Exception{
    public UsernameNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
