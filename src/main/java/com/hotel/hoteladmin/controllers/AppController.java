package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.ExcelExport;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import com.hotel.hoteladmin.utils.Search;
import com.hotel.hoteladmin.utils.Value;
import com.hotel.hoteladmin.utils.tables.Bookings;
import com.hotel.hoteladmin.utils.tables.Customers;
import com.hotel.hoteladmin.utils.tables.Rooms;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML
    private Button btn_cExcelExport;
    @FXML
    private Button btn_bExcelExport;
    @FXML
    private Button btn_rExcelExport;
    @FXML
    private TableView<Bookings> tv_bookings;
    @FXML
    private TextField tf_search;
    @FXML
    private TableColumn<Customers,String> tc_cPassword;
    @FXML
    private TableColumn<Customers,Integer> tc_cUserId;

    @FXML
    private TableColumn<Bookings,Integer> tc_bookingId;
    @FXML
    private TableColumn<Bookings,Integer> tc_btRoomNumber;
    @FXML
    private TableColumn<Bookings,Integer> tc_btUserId;
    @FXML
    private TableColumn<Bookings,String> tc_carParking;

    @FXML
    private TableColumn<Bookings,String> tc_checkIn;

    @FXML
    private TableColumn<Bookings,String> tc_checkOut;
    @FXML
    private TableColumn<Bookings,String> tc_payStatus;

    @FXML
    private TableColumn<Bookings,String> tc_payType;
    @FXML
    private TableColumn<Bookings,String> tc_poolAccess;
    @FXML
    private TableColumn<Bookings,String> tc_roomService;

    @FXML
    private TableColumn<Rooms, Integer> tc_roomNumber;


    @FXML
    private TableColumn<Rooms, String> tc_roomType;

    @FXML
    private TableView<Rooms> tv_rooms;

    @FXML
    private Button btn_newbooking;

    @FXML
    private TableColumn<Customers,String> tc_address;

    @FXML
    private TableColumn<Customers,String> tc_email;

    @FXML
    private TableColumn<Customers,String> tc_firstName;

    @FXML
    private TableColumn<Customers,String> tc_gender;

    @FXML
    private TableColumn<Customers,String> tc_lastName;

    @FXML
    private TableColumn<Customers,Integer> tc_phone;

    @FXML
    private TableView<Customers> tv_customers;

    @FXML
    private Button cutomer_reload;

    @FXML
    private Button btn_logOut;

    @FXML
    private TextField tf_rRoomNumber;

    @FXML
    private ChoiceBox<String> cb_rRoomType;
    @FXML
    private ChoiceBox<String> cb_rRoomStatus;
    @FXML
    private Button btn_roomUpdate;

    private Rooms room;
    private Bookings bookings;
    @FXML
    private Label l_total_money;
    @FXML
    void selectRow(MouseEvent event) {

        room = tv_rooms.getSelectionModel().getSelectedItem();
        tf_rRoomNumber.setText(Integer.toString(room.getRoomNumber()));
        cb_rRoomType.setValue(room.getRoomType());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        l_total_money.setText("$ "+(DButils.getBalance()));
        tv_bookings.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                bookings=tv_bookings.getSelectionModel().getSelectedItem();
                Integer id =bookings.getBookingId();
                Value.setIntegerValue(id);
                String title = "Edit Booking "+id.toString();
                SceneSwitcher.changeSceneToNewWindow("../modifyBooking.fxml",title);
            }
        });
        btn_roomUpdate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DButils.makeRoomVacant(room.getRoomNumber());
            }
        });

        cb_rRoomStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cb_rRoomStatus.getItems().clear();
                cb_rRoomStatus.getItems().add("vacant");
            }
        });


        btn_bExcelExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExcelExport<Bookings> bookingsExcelExport = new ExcelExport<>();
                bookingsExcelExport.export(tv_bookings);
            }
        });
        btn_cExcelExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExcelExport<Customers> bookingsExcelExport = new ExcelExport<>();
                bookingsExcelExport.export(tv_customers);
            }
        });
        btn_rExcelExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExcelExport<Rooms> bookingsExcelExport = new ExcelExport<>();
                bookingsExcelExport.export(tv_rooms);
            }
        });

        btn_logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.closeWindow(event);
                SceneSwitcher.changeSceneToNewWindow("../login.fxml","Log In");
            }
        });
        btn_newbooking.setOnAction(event -> SceneSwitcher.changeSceneToNewWindow("../booking.fxml","New Booking"));
        cutomer_reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                l_total_money.setText("$ "+(DButils.getBalance()));

                // Customer Table
                ObservableList<Customers> searchModelCustomerObservableList = DButils.getCustomerTable();

                tc_cUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
                tc_cPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
                tc_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                tc_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                tc_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                tc_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                tc_email.setCellValueFactory(new PropertyValueFactory<>("email"));
                tc_address.setCellValueFactory(new PropertyValueFactory<>("address"));
                tv_customers.setItems(searchModelCustomerObservableList);

                // Rooms Table
                ObservableList<Rooms> searchModelRoomObservableList = DButils.getRoomsTable();
                tc_roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
                tc_roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
                tv_rooms.setItems(searchModelRoomObservableList);

                //Booking Table
                ObservableList<Bookings> searchModelBookingObservableList = DButils.getBookingTable();
                tc_bookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
                tc_btRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
                tc_btUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
                tc_checkIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
                tc_checkOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
                tc_payType.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
                tc_payStatus.setCellValueFactory(new PropertyValueFactory<>("payStatus"));
                tc_roomService.setCellValueFactory(new PropertyValueFactory<>("roomService"));
                tc_carParking.setCellValueFactory(new PropertyValueFactory<>("carParking"));
                tc_poolAccess.setCellValueFactory(new PropertyValueFactory<>("poolAccess"));

                tv_bookings.setItems(searchModelBookingObservableList);

                //Search
                Search.bookingSearch(tf_search,searchModelBookingObservableList,tv_bookings);
                Search.roomSearch(tf_search,searchModelRoomObservableList,tv_rooms);
                Search.customerSearch(tf_search,searchModelCustomerObservableList,tv_customers);
            }
        });

    }
}
