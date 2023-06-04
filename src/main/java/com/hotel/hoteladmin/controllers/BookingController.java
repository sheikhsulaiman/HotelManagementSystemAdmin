package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.PdfExport;
import com.hotel.hoteladmin.utils.pricechart.PriceChart;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @FXML
    private RadioButton rBtn_print;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        btn_lastUserId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tf_user_id.setText(Integer.toString(DButils.getLastUserId()));
            }
        });


        // new user scene switch
        btn_newuser.setOnAction(event -> SceneSwitcher.changeScene(event,"../signup.fxml","New Registration"));
        cb_roomType.getItems().add("any");
        cb_roomType.getItems().addAll(DButils.getRoomType());
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


        btn_confirmBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DButils.newBooking(Integer.parseInt(cb_roomNo.getValue()), Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue()==null?"Cash":cb_payType.getValue(), cb_payStatus.getValue()==null?"Unpaid":cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO");
                    DButils.createNewInvoice(DButils.getLastBookingId(),PriceChart.calculatePrice(cb_roomNo.getValue(), dp_checkIn.getValue(), dp_checkOut.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO"),cb_payStatus.getValue()==null?"Unpaid":cb_payStatus.getValue());
                    SceneSwitcher.closeWindow(event);
                    int bookingId = DButils.getLastBookingId();
                    if(rBtn_print.isSelected()){
                        PdfExport.printInvoice(DButils.getInvoiceId(bookingId),bookingId,Integer.parseInt(cb_roomNo.getValue()), Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue()==null?"Cash":cb_payType.getValue(), cb_payStatus.getValue()==null?"Unpaid":cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO");
                    }
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
                try {
                    l_predictedPrice.setText("$ " + Integer.toString(PriceChart.calculatePrice(cb_roomNo.getValue(), dp_checkIn.getValue(), dp_checkOut.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO")));
                }catch (NumberFormatException e){}
            }
        });

    }
}