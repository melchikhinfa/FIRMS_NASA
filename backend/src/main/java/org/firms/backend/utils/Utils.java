package org.firms.backend.utils;

import java.util.Base64;
import java.util.Objects;

/**
 * Класс для утилитарных методов
 */
public class Utils {

    /**
     * Метод для кодирования строки в Base64
     * @param stringToEncode строка для кодирования
     * @return закодированная строка
     */
    public static String encodeString(String stringToEncode){
        return Base64.getEncoder().encodeToString(stringToEncode.getBytes());
    }

    /**
     * Метод для проверки закодированного пароля и введенного пароля
     * @param passwordEncoded Закодированный пароль
     * @param passwordClean Введенный пароль без кодирования
     * @return true если пароли совпадают, false в ином случае
     */
    public static boolean checkPasswords(String passwordEncoded, String passwordClean){
        return Objects.equals(passwordEncoded, Utils.encodeString(passwordClean));
    }
}
