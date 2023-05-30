package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.pricechart.PriceChart;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private Label l_predictedPrice;

    @FXML
    private Button btn_confirmBooking;

    @FXML
    private Button btn_lastUserId;

    @FXML
    private Button btn_newuser;

    @FXML
    private Button btn_predictPrice;

    @FXML
    private ChoiceBox<String> cb_payStatus;

    @FXML
    private ChoiceBox<String> cb_payType;

    @FXML
    private ChoiceBox<String> cb_roomNo;

    @FXML
    private ChoiceBox<String> cb_roomType;

    @FXML
    private CheckBox ckb_carParking;

    @FXML
    private CheckBox ckb_poolAccess;

    @FXML
    private CheckBox ckb_roomService;

    @FXML
    private DatePicker dp_checkIn;

    @FXML
    private DatePicker dp_checkOut;

    @FXML
    private TextField tf_user_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cb_payType.getItems().addAll("Cash","Online");
        cb_payStatus.getItems().addAll("Paid","Unpaid");

        dp_checkIn.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        dp_checkOut.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        btn_lastUserId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tf_user_id.setText(Integer.toString(DButils.getLastUserId()));
            }
        });


        // new user scene switch
        btn_newuser.setOnAction(event -> SceneSwitcher.changeScene(event,"../signup.fxml","New Registration"));
        cb_roomType.getItems().addAll(DButils.getRoomType());
        cb_roomType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cb_roomNo.getItems().clear();
                cb_roomNo.getItems().addAll(DButils.getVacantRooms(cb_roomType.getValue()));
            }
        });

        btn_confirmBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.newBooking(Integer.parseInt(cb_roomNo.getValue()),Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue(), cb_payStatus.getValue(), ckb_roomService.isSelected()?"YES":"NO", ckb_poolAccess.isSelected()?"YES":"NO", ckb_carParking.isSelected()?"YES":"NO");
                SceneSwitcher.closeWindow(event);
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Booking Successfull");
                alert.show();
            }
        });

        btn_predictPrice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               l_predictedPrice.setText("$ "+Integer.toString(PriceChart.calculatePrice(cb_roomType.getValue(),dp_checkIn.getValue(),dp_checkOut.getValue(), ckb_roomService.isSelected()?"YES":"NO", ckb_carParking.isSelected()?"YES":"NO", ckb_poolAccess.isSelected()?"YES":"NO")));
            }
        });

    }
}