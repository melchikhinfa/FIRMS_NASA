package org.firms.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import org.firms.client.MainApplication;
import org.firms.client.jsonEntities.in.user.ChangePasswordEntity;
import org.firms.client.requests.Request;
import org.firms.client.requests.URLGenerator;
import org.firms.client.utils.Utils;

import java.io.IOException;
import java.net.http.HttpResponse;

public class ChangePasswordController {
    @FXML
    private PasswordField oldPwdField;
    @FXML
    private PasswordField newPwdField;
    @FXML
    private PasswordField newPwdField2;

    @FXML
    private void cancelOnAction() throws IOException {
        MainApplication.setRoot("main");
    }

    @FXML
    private void changeOnAction() throws IOException, InterruptedException {

        String blankError = checkBlankFields();
        if (!blankError.isBlank()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Ошибка", "Не все поля заполнены", blankError).showAndWait();
            return;
        }
        if(!newPwdField.getText().equals(newPwdField2.getText())){
            Utils.generateAlert(Alert.AlertType.ERROR, "Ошибка", "Пароли не совпадают", "Пароли не совпадают").showAndWait();
            return;
        }

        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setOldPassword(oldPwdField.getText());
        entity.setNewPassword(newPwdField.getText());
        HttpResponse<String> response = Request.postRequest(
                URLGenerator.changePasswordURL(MainApplication.getContext().getUser().getUsername()),
                entity);

        switch (response.statusCode()){
            case 200:
                Utils.generateAlert(Alert.AlertType.INFORMATION, "Успешно", "Пароль успешно изменен", "");
                MainApplication.setRoot(MainApplication.getContext().getMainScreenName());
                break;
            case 401:
                Utils.generateAlert(Alert.AlertType.ERROR, "Ошибка", "Неверный пароль", "");
                return;
            default:
                Utils.generateAlert(Alert.AlertType.ERROR, "Ошибка", "Неизвестная ошибка", "Обратитесь к администратору");
                return;
        }

        MainApplication.setRoot(MainApplication.getContext().getMainScreenName());
    }

    private String checkBlankFields(){
        StringBuilder builder = new StringBuilder();
        if(oldPwdField.getText().isBlank() || newPwdField.getText().isBlank() || newPwdField2.getText().isBlank()){

            if (oldPwdField.getText().isBlank()){
                builder.append(generateBlankErrorText("Текущий пароль"));
            }
            if (newPwdField.getText().isBlank()){
                builder.append(generateBlankErrorText("Новый пароль"));
            }
            if (newPwdField.getText().isBlank()){
                builder.append(generateBlankErrorText("Повторите пароль"));
            }
        }
        return builder.toString();
    }

    private String generateBlankErrorText(String fieldName){
        return "Поле '"+ fieldName +"' не заполнено";
    }
}
