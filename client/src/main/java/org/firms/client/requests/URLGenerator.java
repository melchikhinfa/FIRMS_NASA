package org.firms.client.requests;

import org.firms.client.MainApplication;

/**
 * Генерация URL'ов
 */
public class URLGenerator {
    private static final String BASE_URL = "http://localhost:8080";

    /**
     * URL для получения текущего пользователя
     * @return генерируемый URL
     */
    private static String getCurrentUserURL(){
        return BASE_URL + "/api/users/" + MainApplication.getContext().getUser().getUsername();
    }

    /**
     * URL для авторизации
     * @return генерируемый URL
     */
    public static String authURL(){
        return BASE_URL + "/api/auth";
    }

    /**
     * URL для регистрации пользователя
     * @return генерируемый URL
     */
    public static String signUpURL(){
        return BASE_URL + "/api/signUp";
    }

    /**
     * URL для получения списка подписок
     * @return генерируемый URL
     */
    public static String getSubscriptionsURL(){
        return getCurrentUserURL() + "/subscriptions";
    }

    /**
     * URL для получения списка регионов
     * @return генерируемый URL
     */
    public static String regionsGetURL(){return getSubscriptionsURL() + "/regions";}

    /**
     * URL для получения списка пожаров в радиусе 1км от точки пользователя
     * @return генерируемый URL
     */
    public static String getFiresByCoords(){ return getSubscriptionsURL() + "/fires/notifications";}

    /**
     * URL для добавления точки к подписке
     * @return генерируемый URL
     */
    public static String postSubsCooordinates(String subscriptionId){ return getSubscriptionsURL() + "/" + subscriptionId + "/coordinates";}

    /**
     * URL для удобавления региона к подписке
     * @return генерируемый URL
     */
    public static String postSubscriptionURL(String regionId) {
        return getSubscriptionsURL() + "/regions/" + regionId;
    }
    /**
     * URL для смены пароля
     * @return генерируемый URL
     */
    public static String changePasswordURL(String username){
        return getCurrentUserURL() + "/changePwd";
    }
    /**
     * URL для смены API ключа
     * @return генерируемый URL
     */
    public static String changeApiKey(String username){
        return getCurrentUserURL() + "/changeApiKey";
    }

    /**
     * URL для получения статуса API ключа
     * @return генерируемый URL
     */
    public static String getStatusApiKey(String username){
        return getSubscriptionsURL() + "/apiStatus";
    }

}
