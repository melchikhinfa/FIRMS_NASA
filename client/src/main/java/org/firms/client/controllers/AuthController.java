package org.firms.client.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.firms.client.MainApplication;
import org.firms.client.jsonEntities.in.user.LoginEntity;
import org.firms.client.jsonEntities.out.user.UserEntity;
import org.firms.client.requests.Request;
import org.firms.client.requests.URLGenerator;
import org.firms.client.utils.Context;
import org.firms.client.utils.Utils;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AuthController {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void regOnAction() throws IOException {
        MainApplication.setRoot("signUp");
    }

    @FXML
    private void authOnAction() throws IOException, InterruptedException {
        if(loginField.getText().isBlank() || passwordField.getText().isBlank()){
            Utils.generateAlert(Alert.AlertType.ERROR,
                    "Не все поля заполнены",
                    "Не все поля заполнены",
                    (loginField.getText().isBlank()) ? "Заполните поле 'Логин'" : "Заполните поле 'Пароль'")
                    .showAndWait();
            return;
        }

        LoginEntity entity = new LoginEntity();
        entity.setUsername(loginField.getText());
        entity.setPassword(passwordField.getText());

        HttpResponse<String> response = Request.postRequest(URLGenerator.authURL(), entity);
        switch (response.statusCode()){
            case 200:
                Utils.generateAlert(
                        Alert.AlertType.INFORMATION,
                        "Авторизация успешна",
                        "Вы успешно авторизованы",
                        "")
                        .showAndWait();
                Context context = new Context();
                ObjectMapper objectMapper = new ObjectMapper();
                UserEntity currentUser = objectMapper.readValue(response.body(), new TypeReference<>() {});
                context.setUser(currentUser);
                MainApplication.setContext(context);
                MainApplication.setRoot("main");
                break;
            case 404:
                Utils.generateAlert(
                                Alert.AlertType.ERROR,
                                "Авторизация НЕ успешна",
                                "Пользователя с таким именем не существует",
                                "")
                        .showAndWait();
                break;
            case 401:
                Utils.generateAlert(
                                Alert.AlertType.ERROR,
                                "Авторизация НЕ успешна",
                                "Пароль неверен",
                                "")
                        .showAndWait();
                break;
        }

    }
}
