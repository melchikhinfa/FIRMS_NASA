package org.firms.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.firms.client.MainApplication;
import org.firms.client.jsonEntities.in.user.ChangeAPIEntity;
import org.firms.client.requests.Request;
import org.firms.client.requests.URLGenerator;
import org.firms.client.utils.Utils;

import java.io.IOException;
import java.net.http.HttpResponse;

public class ChangeAPIKey {

    @FXML
    private TextField apiKeyField;

    @FXML
    private void cancelOnAction() throws IOException {
        MainApplication.setRoot(MainApplication.getContext().getMainScreenName());
    }

    @FXML
    private void changeApiOnAction() throws IOException, InterruptedException {

        if (apiKeyField.getText().isBlank()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Ошибка", "Не все поля заполнены", "Заполните поле 'Новый ключ'").showAndWait();
            return;
        }
        ChangeAPIEntity entity = new ChangeAPIEntity();
        entity.setApiKey(apiKeyField.getText());

        HttpResponse<String> response = Request.postRequest(URLGenerator.changeApiKey(MainApplication.getContext().getUser().getUsername()), entity);

        if(response.statusCode()==200){
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Успешно", "Ключ успешно изменен", "").showAndWait();
            MainApplication.setRoot(MainApplication.getContext().getMainScreenName());
        }
    }
}
