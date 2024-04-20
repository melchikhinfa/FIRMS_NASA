package org.firms.backend.exceptions;

/**
 * Exception: Пользователь с таким именем уже существует
 */
public class UsernameExistsException extends Exception{
    public UsernameExistsException(String errorMessage){
        super(errorMessage);
    }
}
