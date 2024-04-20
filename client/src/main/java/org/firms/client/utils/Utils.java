package org.firms.client.utils;

import javafx.scene.control.Alert;
import org.firms.client.MainApplication;

/**
 * Утилитарный класс
 */
public class Utils {

    /**
     * Генерация ALERT
     * @param alertType тип вспылваемого окна
     * @param title подпись
     * @param header заголовок
     * @param text текст всплываемого окна
     * @return
     */
    public static Alert generateAlert(Alert.AlertType alertType, String title, String header, String text){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        return alert;

    }

    /**
     * Генерация ФИО пользователя
     * @return
     */
    public static String generateFIOLabelText(){
        StringBuilder builder = new StringBuilder();
        builder.append(MainApplication.getContext().getUser().getLastName());
        builder.append(" ");
        builder.append(MainApplication.getContext().getUser().getFirstName());
        builder.append(" ");
        builder.append(MainApplication.getContext().getUser().getMiddleName());
        builder.append(" (" + MainApplication.getContext().getUser().getUsername() + ")");
        return builder.toString();
    }
}
