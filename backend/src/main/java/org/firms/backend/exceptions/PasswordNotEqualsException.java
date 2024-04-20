package org.firms.backend.exceptions;

/**
 * Exception неравенста паролей
 */
public class PasswordNotEqualsException extends Exception{
    public PasswordNotEqualsException(String errorMessage){
        super(errorMessage);
    }
}
