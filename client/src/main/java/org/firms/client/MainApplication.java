package org.firms.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.firms.client.utils.Context;

import java.io.IOException;

/**
 * Класс генерации JavaFX приложения
 */
public class MainApplication extends Application {

    /**
     * Контекст приложения
     */
    private static Context context;


    public static void setContext(Context context){
        MainApplication.context = context;
    }

    public static Context getContext(){
        return context;
    }

    /**
     * Отображаемая сцена
     */
    private static Scene scene;

    /**
     * Метод запуска приложения
     * @param stage окно приожения
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("auth"), 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Загрузка fxml файла
     * @param fxml имя fxml файла
     * @return
     * @throws IOException файл не найден
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Установка текущего отображаемого окна
     * @param fxml имя fxml файла
     * @throws IOException файл не найден
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    /**
     * Точка входа
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}