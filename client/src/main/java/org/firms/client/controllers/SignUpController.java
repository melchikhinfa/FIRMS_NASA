package org.firms.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.firms.client.MainApplication;
import org.firms.client.jsonEntities.in.user.SignUpEntity;
import org.firms.client.requests.Request;
import org.firms.client.requests.URLGenerator;
import org.firms.client.utils.Utils;

import java.io.IOException;
import java.net.http.HttpResponse;

public class SignUpController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField apiKeyField;

    @FXML
    private void authOnAction() throws IOException {
        MainApplication.setRoot("auth");
    }
    @FXML
    private void regOnAction() throws IOException, InterruptedException {
        String error = errorText();
        if (!error.isBlank()){
            Utils.generateAlert(Alert.AlertType.ERROR,
                    "Не все поля заполнены",
                    "Заполните следующие поля",
                    error).showAndWait();
            return;
        }
        SignUpEntity entity = new SignUpEntity();
        entity.setUsername(loginField.getText());
        entity.setPassword(passwordField.getText());
        entity.setApiKey(apiKeyField.getText());
        entity.setFirstName(firstNameField.getText());
        entity.setLastName(lastNameField.getText());
        entity.setMiddleName(middleNameField.getText());
        HttpResponse<String> response = Request.postRequest(URLGenerator.signUpURL(), entity);
        switch (response.statusCode()){
            case 201:
                Utils.generateAlert(
                                Alert.AlertType.INFORMATION,
                                "Регистрация успешна",
                                "Вы успешно зарегистрированы",
                                "")
                        .showAndWait();
                MainApplication.setRoot("auth");
                break;
            case 409:
                Utils.generateAlert(
                                Alert.AlertType.ERROR,
                                "Регистрация отклонена",
                                "Пользователь с таким именем уже существует",
                                "")
                        .showAndWait();
                break;
        }
    }

    private String errorText(){
        StringBuilder builder = new StringBuilder();
        if(loginField.getText().isBlank()){
            builder.append("Поле 'логин' пустое\n");
        }
        if(passwordField.getText().isBlank()){
            builder.append("Поле 'Пароль' пустое\n");
        }
        if(firstNameField.getText().isBlank()){
            builder.append("Поле 'Имя' пустое\n");
        }
        if(lastNameField.getText().isBlank()){
            builder.append("Поле 'Фамилия' пустое\n");
        }
        if(middleNameField.getText().isBlank()){
            builder.append("Поле 'Отчество' пустое\n");
        }
        if(apiKeyField.getText().isBlank()){
            builder.append("Поле 'Ключ FIRMS' пустое\n");
        }

        return builder.toString();
    }
}
