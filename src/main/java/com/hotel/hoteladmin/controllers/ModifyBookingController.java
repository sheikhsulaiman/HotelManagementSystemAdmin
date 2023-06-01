package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import com.hotel.hoteladmin.utils.Value;
import com.hotel.hoteladmin.utils.pricechart.PriceChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.sqlite.core.DB;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyBookingController implements Initializable {

    @FXML
    private Button btn_cancelBooking;

    @FXML
    private Button btn_predictPrice;

    @FXML
    private Button btn_printReceipt;

    @FXML
    private Button btn_updateBooking;

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
    private Label l_predictedPrice;

    @FXML
    private TextField tf_user_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> list = new ArrayList<>(9);

        list.addAll(DButils.getBookingDetails(Value.getIntegerValue()));
       tf_user_id.setText(list.get(1));
       dp_checkIn.setValue(LocalDate.parse(list.get(2)));
        dp_checkOut.setValue(LocalDate.parse(list.get(3)));
        cb_roomNo.setValue(list.get(0));
        cb_roomType.setValue(DButils.getRoomType(list.get(0)));
        cb_payType.setValue(list.get(4));
        cb_payStatus.setValue(list.get(5));
        if(list.get(6).equals("YES")) {
            ckb_roomService.setSelected(true);
        }else {
            ckb_roomService.setSelected(false);
        }
        if(list.get(7).equals("YES")) {
            ckb_poolAccess.setSelected(true);
        }else {
            ckb_poolAccess.setSelected(false);
        }
        if(list.get(8).equals("YES")) {
            ckb_carParking.setSelected(true);
        }else {
            ckb_carParking.setSelected(false);
        }
        l_predictedPrice.setText("$ "+Integer.toString(PriceChart.calculatePrice(cb_roomNo.getValue(),dp_checkIn.getValue(),dp_checkOut.getValue(), ckb_roomService.isSelected()?"YES":"NO", ckb_carParking.isSelected()?"YES":"NO", ckb_poolAccess.isSelected()?"YES":"NO")));



        cb_payType.getItems().addAll("Cash","Online");
        cb_payStatus.getItems().addAll("Paid","Unpaid");

        dp_checkIn.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                LocalDate today = LocalDate.now();
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        dp_checkOut.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today;
                if(dp_checkIn.getValue()!=null)
                    today = dp_checkIn.getValue();
                else
                    today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        cb_roomType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    cb_roomNo.getItems().clear();
                    ArrayList<String> availableRooms = new ArrayList<>(9);
                    availableRooms = (DButils.getRooms(cb_roomType.getValue()));
                    availableRooms.removeAll(DButils.getBookedRooms(dp_checkIn, dp_checkOut));

                    cb_roomNo.getItems().addAll(availableRooms);
                }catch (NullPointerException e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Please pick a date");
                    alert.show();
                }
            }
        });



        cb_roomNo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //DButils.updateCalendar(Integer.parseInt(cb_roomNo.getValue()));
            }
        });

        btn_updateBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DButils.updateBooking(Integer.parseInt(list.get(9)),Integer.parseInt(cb_roomNo.getValue()), Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue(), cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO");
                    SceneSwitcher.closeWindow(event);
                    Alert alert =new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Booking Successfull");
                    alert.show();
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill up all the fields");
                    alert.show();
                }
            }
        });

        btn_predictPrice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                l_predictedPrice.setText("$ "+Integer.toString(PriceChart.calculatePrice(cb_roomNo.getValue(),dp_checkIn.getValue(),dp_checkOut.getValue(), ckb_roomService.isSelected()?"YES":"NO", ckb_carParking.isSelected()?"YES":"NO", ckb_poolAccess.isSelected()?"YES":"NO")));
            }
        });
    }
}
