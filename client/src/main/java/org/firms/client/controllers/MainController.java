package org.firms.client.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import org.firms.client.MainApplication;
import org.firms.client.jsonEntities.out.subscription.FireEntity;
import org.firms.client.jsonEntities.out.subscription.GetStatusAPIKey;
import org.firms.client.requests.Request;
import org.firms.client.requests.URLGenerator;
import org.firms.client.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label fioLabel;

    @FXML
    private AnchorPane anchorPane;

    private MapView mapView;

    private CustomMapLayer csm;

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

        fioLabel.setText(Utils.generateFIOLabelText());

        try {
            if(Request.getRequest(URLGenerator.getSubscriptionsURL()).statusCode() == 200){
                showMap();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private MapView createMapView() throws IOException, InterruptedException {
        mapView = new MapView();
        mapView.setPrefSize(900,600);
        HttpResponse<String> response = Request.getRequest(URLGenerator.getSubscriptionsURL() + "/fires");
        ObjectMapper om = new ObjectMapper();
        List<FireEntity> points = om.readValue(response.body(), new TypeReference<>(){});

        csm = new CustomMapLayer();
        assert points != null;
        for(FireEntity point: points){
            Node icon = new Circle(3, Color.RED);
            csm.addPoint(new MapPoint(point.getLatitude(), point.getLongitude()), icon);
        }
        mapView.addLayer(csm);
        mapView.setZoom(5);
        return mapView;
    }

    @FXML
    private void showPoints(){
        showMap();

    }

    private void showMap(){
        mapView = null;
        try {
            mapView = createMapView();
            anchorPane.getChildren().add(mapView);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
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
