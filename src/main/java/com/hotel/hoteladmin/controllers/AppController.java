package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.ExcelExport;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import com.hotel.hoteladmin.utils.Search;
import com.hotel.hoteladmin.utils.Value;
import com.hotel.hoteladmin.utils.tables.Bookings;
import com.hotel.hoteladmin.utils.tables.Customers;
import com.hotel.hoteladmin.utils.tables.Rooms;
import com.hotel.hoteladmin.utils.tables.RoomsDetails;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
    private TextField tf_rNewRoomType;
    @FXML
    private TextField tf_rNewRoomNumber;
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
    private Button btn_addRoom;

    @FXML
    private Button btn_deleteRoom;

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
    private TableView<RoomsDetails> tv_roomDetails;
    @FXML
    private TableColumn<RoomsDetails, Integer> tc_rBookingid;

    @FXML
    private TableColumn<RoomsDetails, String> tc_rCheckIn;

    @FXML
    private TableColumn<RoomsDetails, String> tc_rCheckOut;

    @FXML
    private TableColumn<RoomsDetails, Integer> tc_rUserId;

    @FXML
    private Button cutomer_reload;

    @FXML
    private Button btn_logOut;

    @FXML
    private Button btn_rdExcelExport;

    private Rooms room;
    private Bookings bookings;
    @FXML
    private Label l_total_money;
    @FXML
    private Label l_ipAddress;
    @FXML
    void hideIp(MouseEvent event) {
        l_ipAddress.setVisible(false);
    }
    @FXML
    void showIp(MouseEvent event) {
        l_ipAddress.setVisible(true);
    }

    private int selectedRoomNumber;
    @FXML
    void selectRow(MouseEvent event) {

        room = tv_rooms.getSelectionModel().getSelectedItem();
        selectedRoomNumber = room.getRoomNumber();

        // RoomsDetails Table
        ObservableList<RoomsDetails> searchModelRoomsDetailsObservableList = DButils.getSelectedRoomDetails(selectedRoomNumber);

        tc_rBookingid.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        tc_rUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tc_rCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        tc_rCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        tv_roomDetails.setItems(searchModelRoomsDetailsObservableList);

        Search.roomDetailsSearch(tf_search,searchModelRoomsDetailsObservableList,tv_roomDetails);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadEveryThing();

        try {
            l_ipAddress.setText("IP: " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            l_ipAddress.setText("Ip not found.");
        }

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
        btn_rdExcelExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExcelExport<RoomsDetails> bookingsExcelExport = new ExcelExport<>();
                bookingsExcelExport.export(tv_roomDetails);
            }
        });

        btn_logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.changeScene(event,"../login.fxml","Log In");
            }
        });
        btn_newbooking.setOnAction(event -> SceneSwitcher.changeSceneToNewWindow("../booking.fxml","New Booking"));
        cutomer_reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadEveryThing();
            }
        });

        btn_addRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<String> existingRooms = new ArrayList<>(9);
                existingRooms.addAll(DButils.getRooms("any"));
                String newRoomNumber = tf_rNewRoomNumber.getText();
                if((tf_rNewRoomNumber.getText().isEmpty()||tf_rNewRoomNumber.getText().isBlank())||(tf_rNewRoomType.getText().isEmpty()||tf_rNewRoomType.getText().isBlank())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill up all the fields");
                    alert.show();
                }else {
                    if (existingRooms.contains(newRoomNumber)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Room Number " + newRoomNumber + " already exists\nPlease insert an unique room number.");
                        alert.show();
                    } else {
                        DButils.addRoom(Integer.parseInt(tf_rNewRoomNumber.getText()), tf_rNewRoomType.getText().toLowerCase());
                        loadEveryThing();
                    }
                }
            }
        });

        btn_deleteRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(String.valueOf(selectedRoomNumber).isEmpty()||String.valueOf(selectedRoomNumber).isBlank()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please select a room to delete");
                    alert.show();
                }else {
                    DButils.deleteRoom(selectedRoomNumber);
                    loadEveryThing();
                }
            }
        });

    }
    public void loadEveryThing(){
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
}
