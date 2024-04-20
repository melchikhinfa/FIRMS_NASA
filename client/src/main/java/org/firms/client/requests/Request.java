package org.firms.client.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Запросы
 */
public class Request {

    /**
     * POST запрос с телом
     * @param url - ссылка
     * @param entity - тело запроса
     * @return client.send - отправка запроса
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> postRequest(String url, Object entity) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(ow.writeValueAsString(entity)))
                .header("Content-Type", "application/json")
                .build();

        return client
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * POST запрос без тела
     * @param url строка подключения
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> postRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        // ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("Content-Type", "application/json")
                .build();

        return client
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * DELETE запрос
     * @param url ссылка
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> deleteRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();

        return client
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * get заропс
     * @param url ссылка
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> getRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .GET()
                .header("Content-Type", "application/json")
                .build();

        return client
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
