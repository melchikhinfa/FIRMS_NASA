package org.firms.client.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.firms.client.MainApplication;
import org.firms.client.jsonEntities.out.subscription.CoordinatesDTO;
import org.firms.client.jsonEntities.out.subscription.FireEntity;
import org.firms.client.jsonEntities.out.subscription.GetStatusAPIKey;
import org.firms.client.jsonEntities.out.subscription.SubscriptionEntity;
import org.firms.client.requests.Request;
import org.firms.client.requests.URLGenerator;
import org.firms.client.utils.Utils;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label fioLabel;

    @FXML
    private AnchorPane anchorPane;

    private MapView mapView;

    private CustomMapLayer csm;

    private boolean canAddLocation = false;

    private VBox notificationsBox;

    @FXML
    private void exitOnAction(){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void quitOnAction() throws IOException {
        MainApplication.setRoot("auth");
    }

    @FXML
    private void changePwdOnAction() throws IOException {
        MainApplication.setRoot("changePwd");
    }

    @FXML
    private void changeAPIonAction() throws IOException{
        MainApplication.setRoot("changeApiKey");
    }

    @FXML
    private void aboutAuthorOnAction(){
        Utils.generateAlert(
                Alert.AlertType.INFORMATION,
                "Об Авторе",
                "Выполнил Мельчихин А.В.",
                "Студент группы ЗБ-ПИ21-1"
        ).showAndWait();
    }

    @FXML
    private void checkApiStatusKeyOnAction() throws IOException, InterruptedException {
        HttpResponse<String> response = Request.getRequest(URLGenerator.getStatusApiKey(MainApplication.getContext().getUser().getUsername()));
        ObjectMapper objectMapper = new ObjectMapper();
        GetStatusAPIKey api = objectMapper.readValue(response.body(), new TypeReference<>() {});
        Utils.generateAlert(
                Alert.AlertType.INFORMATION,
                "Информация о ключе",
                "Информация о ключе",
                "Максимальное количество транзакций: " + api.getTransactionLimit() + "\nОставшееся количество транзакций: " + api.getCurrentTransactions()
        ).showAndWait();
    }

    @FXML
    private void openSubsOnAction() throws IOException {
        MainApplication.getContext().setMainScreenName("subscriptions");
        MainApplication.setRoot("subscriptions");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addLocationButton.setVisible(true);
//        addLocationButton.setOnAction(event -> handleAddLocation());
        fioLabel.setText(Utils.generateFIOLabelText());

        try {
            if(Request.getRequest(URLGenerator.getSubscriptionsURL()).statusCode() == 200){
                showMap();
                setupAutoRefresh();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    private MapView createMapView() throws IOException, InterruptedException {
        mapView = new MapView();
        mapView.setPrefSize(900,600);

        MapPoint initialCenter = new MapPoint(55.73906989365343,  37.487185180699285);
        mapView.setCenter(initialCenter);
        mapView.setZoom(10);


        try {
            HttpResponse<String> response = Request.getRequest(URLGenerator.getSubscriptionsURL() + "/fires"); // запрос на получение списка пожаров
            ObjectMapper om = new ObjectMapper();

            HttpResponse<String> response2 = Request.getRequest(URLGenerator.getSubscriptionsURL()); // запрос на получение списка подписок

            if (Objects.equals(response2.body(), "")) {
                Utils.generateAlert(
                        Alert.AlertType.WARNING,
                        "Внимание!",
                        "Не выбрана подписка!",
                        "Пожалуйста выберите подписку"
                ).showAndWait();
            } else {
                List<SubscriptionEntity> subs = om.readValue(response2.body(), new TypeReference<>() {
                });
                List<FireEntity> points = om.readValue(response.body(), new TypeReference<>() {
                });
                assert subs != null && points != null;
                csm = new CustomMapLayer();
                for (FireEntity point : points) {
                    Node icon = new Circle(3, Color.RED);
                    csm.addPoint(new MapPoint(point.getLatitude(), point.getLongitude()), icon);
                } // отрисовываем полученные fire entity точками на карте

                Node userIcon = new Circle(7, Color.BLUE);
                MapPoint userPoint = new MapPoint(subs.get(0).getLatitude(), subs.get(0).getLongitude());
                csm.addPoint(userPoint, userIcon);

                mapView.addLayer(csm);
                mapView.setCenter(userPoint);
                mapView.setZoom(13);
                updateFireList();
//                return mapView;
            }


        } catch (MismatchedInputException e) {
            e.printStackTrace();
            return mapView;
        }

        return mapView;
    }

    /**
     * Кнопка отображения точек на карте
     */
    @FXML
    private void showPoints(){
        showMap();
        canAddLocation = true;
        addLocationButton.setVisible(true);
    }

    /**
     * Метод для отображения карты и уведомлений
     */
    private void showMap(){
        mapView = null;
        try {
            mapView = createMapView();
            anchorPane.getChildren().add(mapView);
            addLocationButton.setOnAction(event -> handleAddLocation());
            // сразу добавляем боксы уведомлений о пожарах при отрисовке карты с пользовательской точкой
            notificationsBox = new VBox(10); // 10 - пространство между элементами
            notificationsBox.setPadding(new Insets(10)); // Отступы вокруг VBox
            notificationsBox.setStyle("-fx-background-color: #EEE;"); // Цвет фона

            anchorPane.getChildren().add(notificationsBox);
            anchorPane.setTopAnchor(notificationsBox, 10.0);
            anchorPane.setRightAnchor(notificationsBox, 10.0);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Кнопка добавления местоположения
     */
    @FXML
    private Button addLocationButton; // кнопка выбора отметки на карте

    /**
     * Обработчик кнопки добавления местоположения
     */
    @FXML
    private void handleAddLocation() {
        canAddLocation = true;
        // Устанавливаем событие, которое активируется при клике по карте
        EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isStillSincePress() && event.getClickCount() == 1 |  event.getClickCount() == 1) {
                    try {
                        Point2D mapPoint = new Point2D(event.getX(), event.getY());
                        MapPoint geoPoint = mapView.getMapPosition(mapPoint.getX(), mapPoint.getY());
                        addCustomPoint(geoPoint);
                        addCoordinatesToSubs(geoPoint);
                        event.consume(); // Прекращаем дальнейшую обработку события
                        // Удаляем обработчик события, чтобы больше не реагировать на клики
                        mapView.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                        canAddLocation = false; // Делаем кнопку неактивной
                        updateFireList();
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        // Добавляем обработчик события на карту, только если это разрешено
        if (canAddLocation){
            mapView.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
            canAddLocation = false; // После установки обработчика события делаем кнопку неактивной, чтобы избежать повторного добавления
        } else {
            Utils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Ошибка",
                    "Вы не можете добавить местоположение, пока не выбрана подписка",
                    "Пожалуйста выберите подписку"
            ).showAndWait();
        }
    }

    /**
     * Добавление на карту точки пользователя
     * @param geoPoint - точка с координатами на карте
     * @throws IOException
     * @throws InterruptedException
     */
    private void addCustomPoint(MapPoint geoPoint) throws IOException, InterruptedException {
        Node icon = new Circle(5, Color.BLUE); // Синий цвет для пользовательских точек
        if (csm != null) {
            csm.addPoint(geoPoint, icon);
            icon.setVisible(true);
        } else {
            Utils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Ошибка",
                    "Вы не можете добавить местоположение, пока не выбрана подписка",
                    "Пожалуйста выберите подписку"
            ).showAndWait();
        }

    }

    /**
     * Установка авторефреша карты каждые 15 минут
     */
    @FXML
    private void setupAutoRefresh() {
        // Событие, которое будет повторяться каждые 15 минут
        KeyFrame keyFrame = new KeyFrame(Duration.minutes(15), event -> {
            Platform.runLater(() -> {
                try {
                    // делаем авторефреш карты
                    createMapView();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        // Timeline, который запускает KeyFrame
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Бесконечное повторение
        timeline.play(); // Запуск таймлайна
    }

    /**
     * Добавление отмеченной точки в подписку пользователя
     * @param geoPoint - точка на карте
     * @throws IOException
     * @throws InterruptedException
     */
    private void addCoordinatesToSubs(MapPoint geoPoint) throws IOException, InterruptedException {
        // забираем координаты из отмеченной точки для отправки бэку
        CoordinatesDTO entity = new CoordinatesDTO();
        entity.setLatitude(geoPoint.getLatitude());
        entity.setLongitude(geoPoint.getLongitude());

        HttpResponse<String> response2 = Request.getRequest(URLGenerator.getSubscriptionsURL()); // запрос на получение списка подписок
        ObjectMapper om2 = new ObjectMapper();
        List<SubscriptionEntity> subs = om2.readValue(response2.body(), new TypeReference<>(){});
        String subscriptionId = subs.get(0).getId().toString(); // id подписки, берем первую подписку RUS

        try {
            HttpResponse<String> response = Request.postRequest(URLGenerator.postSubsCooordinates(subscriptionId), entity);
            if (response.statusCode() == 200) {
                Utils.generateAlert(
                                Alert.AlertType.INFORMATION,
                                "Отслеживание координат",
                                "Координаты успешно добавлены",
                                "Теперь вы будете получать уведомления о пожарах по данному местоположению")
                        .showAndWait();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод добавления уведомления
     * @param notification - строчное уведомление
     */
    public void addNotification(String notification) {
        Label label = new Label(notification);
        label.setWrapText(true);

        Button closeButton = new Button("❌");
        closeButton.setOnAction(e -> notificationsBox.getChildren().remove(label.getParent()));

        HBox hbox = new HBox(5, label, closeButton); // 5 - расстояние между элементами в HBox
        Platform.runLater(() -> notificationsBox.getChildren().add(hbox));
    }

    /**
     * Метод обновляющий список уведомлений (для вывода боксов увдомлений)
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
//    private void updateFireList(MapPoint geoPoint) throws IOException, InterruptedException {
    private void updateFireList() throws IOException, InterruptedException {
        List<FireEntity> fireList = getFiresByCoordinates();
        for(FireEntity fireItem: fireList) {
            String item = fireItem.toString();
            addNotification(item);
        }
    }

    /**
     * Получение отфильтрованного списка пожров в радиусе 1км от точки пользователя
     * @return - Спискок FireEntity пожаров в радиусе 1км от точки
     * @throws IOException
     * @throws InterruptedException
     */
    private List<FireEntity> getFiresByCoordinates() throws IOException, InterruptedException {
        HttpResponse<String> response = Request.getRequest(URLGenerator.getFiresByCoords());
        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } else {
            return new ArrayList<>(); // Возвращение пустого списка в случае других ошибок
        }
    }


    private static class CustomMapLayer extends MapLayer {
        private final ObservableList<Pair<MapPoint, Node>> points = FXCollections.observableArrayList();


        public CustomMapLayer() throws IOException, InterruptedException {

        }

        public void addPoint(MapPoint p, Node icon) {
            points.add(new Pair<>(p, icon));
            this.getChildren().add(icon);
            this.markDirty();
        }
        @Override
        protected void layoutLayer() {
            for (Pair<MapPoint, Node> candidate : points) {
                MapPoint point = candidate.getKey();
                Node icon = candidate.getValue();
                Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
                icon.setVisible(true);
                icon.setTranslateX(mapPoint.getX());
                icon.setTranslateY(mapPoint.getY());
            }
        }
    }
}
