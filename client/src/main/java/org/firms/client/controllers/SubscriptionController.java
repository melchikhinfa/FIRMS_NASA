package org.firms.client.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.firms.client.MainApplication;
import org.firms.client.jsonEntities.out.region.RegionEntity;
import org.firms.client.jsonEntities.out.subscription.GetStatusAPIKey;
import org.firms.client.jsonEntities.out.subscription.SubscriptionEntity;
import org.firms.client.requests.Request;
import org.firms.client.requests.URLGenerator;
import org.firms.client.tableEntities.RegionTableView;
import org.firms.client.tableEntities.SubscriptionTableView;
import org.firms.client.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SubscriptionController implements Initializable {
    @FXML
    private Label fioLabel;

    private ObservableList<SubscriptionTableView> subscriptionsData;


    private ObservableList<RegionTableView> regionsData;

    @FXML
    private TableView<SubscriptionTableView> subsTable;

    @FXML
    private TableColumn<SubscriptionTableView, String> shortNameSub;

    @FXML
    private TableColumn<SubscriptionTableView, String> fullNameSub;

    @FXML
    private TableView<RegionTableView> regionsTable;

    @FXML
    private TableColumn<RegionTableView, String> shortNameRegion;

    @FXML
    private TableColumn<RegionTableView, String> fullNameRegion;

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
    private void openMapOnAction() throws IOException {
        MainApplication.getContext().setMainScreenName("main");
        MainApplication.setRoot("main");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        regionsTable.setRowFactory(tv -> {
            TableRow<RegionTableView> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    RegionTableView rowData = row.getItem();
                    Alert alert = Utils.generateAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Добавление",
                            "Добавить регион " + rowData.getShortName(),
                            "Вы хотите добавить регион " + rowData.getFullName()
                    );
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        try {
                            Request.postRequest(URLGenerator.postSubscriptionURL(rowData.getId().toString()));
                            init();
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            return row ;
        });

        subsTable.setRowFactory(tv -> {
            TableRow<SubscriptionTableView> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    SubscriptionTableView rowData = row.getItem();
                    Alert alert = Utils.generateAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Удаление",
                            "Удалить регион " + rowData.getShortName(),
                            "Вы хотите удалить регион " + rowData.getFullName()
                    );
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        try {
                            Request.deleteRequest(URLGenerator.getSubscriptionsURL() + "/" + rowData.getId());
                            init();
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            return row ;
        });

        regionsData = FXCollections.observableArrayList();
        subscriptionsData = FXCollections.observableArrayList();
        fioLabel.setText(Utils.generateFIOLabelText());
        try {
            init();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws IOException, InterruptedException {
        HttpResponse<String> response = Request.getRequest(URLGenerator.regionsGetURL());
        ObjectMapper om = new ObjectMapper();
        List<RegionEntity> regions = om.readValue(response.body(), new TypeReference<>(){});



        shortNameRegion.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        fullNameRegion.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        regionsData.setAll(regions.stream().map(regionEntity -> new RegionTableView(
                regionEntity.getId(),
                regionEntity.getShortName(),
                regionEntity.getName()
        )).collect(Collectors.toList()));
        regionsTable.setItems(regionsData);

        HttpResponse<String> response2 = Request.getRequest(URLGenerator.getSubscriptionsURL());
        ObjectMapper om2 = new ObjectMapper();
        try{
            List<SubscriptionEntity> subs = om2.readValue(response2.body(), new TypeReference<>(){});
            shortNameSub.setCellValueFactory(new PropertyValueFactory<>("shortName"));
            fullNameSub.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            subscriptionsData.setAll(subs.stream().map(mapEntity -> new SubscriptionTableView(
                    mapEntity.getId(),
                    mapEntity.getShortName(),
                    mapEntity.getRegionName()
            )).collect(Collectors.toList()));
            subsTable.setItems(subscriptionsData);
        }catch (MismatchedInputException e){
            return;
        }



    }
}
