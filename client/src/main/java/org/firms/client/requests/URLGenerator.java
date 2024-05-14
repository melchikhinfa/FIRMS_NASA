package org.firms.client.requests;

import org.firms.client.MainApplication;

/**
 * Генерация URL'ов
 */
public class URLGenerator {
    private static final String BASE_URL = "http://localhost:8080";

    private static String getCurrentUserURL(){
        return BASE_URL + "/api/users/" + MainApplication.getContext().getUser().getUsername();
    }
    public static String authURL(){
        return BASE_URL + "/api/auth";
    }

    public static String signUpURL(){
        return BASE_URL + "/api/signUp";
    }
    public static String getSubscriptionsURL(){
        return getCurrentUserURL() + "/subscriptions";
    }
    public static String regionsGetURL(){return getSubscriptionsURL() + "/regions";}

    public static String getFiresByCoords(){ return getSubscriptionsURL() + "/fires/notifications";}

    public static String postSubsCooordinates(String subscriptionId){ return getSubscriptionsURL() + "/" + subscriptionId + "/coordinates";}

    public static String postSubscriptionURL(String regionId) {
        return getSubscriptionsURL() + "/regions/" + regionId;
    }

    public static String changePasswordURL(String username){
        return getCurrentUserURL() + "/changePwd";
    }

    public static String changeApiKey(String username){
        return getCurrentUserURL() + "/changeApiKey";
    }


    public static String getStatusApiKey(String username){
        return getSubscriptionsURL() + "/apiStatus";
    }

}
