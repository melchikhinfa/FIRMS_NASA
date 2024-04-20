package org.firms.backend.firmsRequests;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.firms.backend.jsonEntities.out.subscription.FireEntity;
import org.firms.backend.jsonEntities.out.subscription.GetStatusAPIKey;
import org.firms.backend.models.Region;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Запросы к FIRM
 */
public class FirmsRequests {
    /**
     * Запрос на получение статуса
     * @param MAP_KEY - ключ
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static GetStatusAPIKey getStatusRequest(String MAP_KEY) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URLGenerator.getStatusURL(MAP_KEY)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject obj = new JSONObject(response.body());

        GetStatusAPIKey responseObject = new GetStatusAPIKey();
        responseObject.setCurrentTransactions(obj.getInt("current_transactions"));
        responseObject.setTransactionLimit(obj.getInt("transaction_limit"));

        return responseObject;
    }

    /**
     * Получение списка стран
     * @return
     * @throws IOException
     * @throws CsvValidationException
     * @throws InterruptedException
     */
    public static List<Region> getCountries() throws IOException, CsvValidationException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URLGenerator.getCountryListURL()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        CSVReader csvReader = new CSVReaderBuilder(
                new StringReader(
                        response.body())
        )
                .withCSVParser(
                        new CSVParserBuilder().withSeparator(';')
                                .build())
                .build();

        String[] nextLine;
        List<Region> regions = new ArrayList<>();
        while ((nextLine = csvReader.readNext()) != null) {
            String firstColumn = nextLine[1];
            if(Objects.equals(firstColumn, "abreviation")){
                continue;
            }
            String secondColumn = nextLine[2];
            Region temp = new Region();
            temp.setShortName(firstColumn);
            temp.setName(secondColumn);
            regions.add(temp);
        }

        csvReader.close();

        return regions;
    }

    /**
     * Получение пожаров по стране
     * @param MAP_KEY API ключ
     * @param COUNTRY_CODE код страны
     * @param DATE_RANGE даты
     * @return
     * @throws CsvValidationException
     * @throws IOException
     * @throws InterruptedException
     */
    public static List<FireEntity> getFiresFromCountry(String MAP_KEY, String COUNTRY_CODE, String DATE_RANGE) throws CsvValidationException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URLGenerator.getFires(MAP_KEY, DATE_RANGE, COUNTRY_CODE)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        CSVReader csvReader = new CSVReaderBuilder(
                new StringReader(
                        response.body())
        )
                .withCSVParser(
                        new CSVParserBuilder().withSeparator(',')
                                .build())
                .build();

        String[] nextLine;
        List<FireEntity> entities = new ArrayList<>();
        while ((nextLine = csvReader.readNext()) != null) {
            String firstColumn = nextLine[1];
            if(Objects.equals(firstColumn, "latitude")){
                continue;
            }
            String secondColumn = nextLine[2];
            FireEntity temp = new FireEntity();
            temp.setLatitude(Double.parseDouble(firstColumn));
            temp.setLongitude(Double.parseDouble(secondColumn));
            entities.add(temp);
        }

        csvReader.close();
        return entities;
    }
}
