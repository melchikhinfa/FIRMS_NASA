package org.firms.backend.firmsRequests;

/**
 * Класс генератора URL'ов к FIRMS
 */
public class URLGenerator {
    /**
     * Константа основного адреса
     */
    private static final String BASE_URL = "https://firms.modaps.eosdis.nasa.gov";

    /**
     * URL на получение списка стран
     * @return
     */
    public static String getCountryListURL(){
        return BASE_URL + "/api/countries/?format=csv";
    }

    /**
     * URL на получение статуса ключа
     * @param MAP_KEY - API ключ
     * @return
     */
    public static String getStatusURL(String MAP_KEY){
        return BASE_URL + "/mapserver/mapkey_status/?MAP_KEY=" + MAP_KEY;
    }

    /**
     * Получение списка пожаров
     * @param MAP_KEY - API ключ
     * @param DATE_RANGE - За количество дней
     * @param COUNTRY_CODE - Код страны
     * @return
     */
    public static String getFires(String MAP_KEY, String DATE_RANGE, String COUNTRY_CODE){
        return BASE_URL + "/api/country/csv/" + MAP_KEY + "/VIIRS_SNPP_NRT/" + COUNTRY_CODE + "/" + DATE_RANGE;
    }
}
