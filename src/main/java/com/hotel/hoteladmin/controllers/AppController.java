package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import com.hotel.hoteladmin.utils.tables.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                ObservableList<Customers> searchModelCustomerObservableList = DButils.getCustomerTable();
                tc_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                tc_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                tc_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                tc_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                tc_email.setCellValueFactory(new PropertyValueFactory<>("email"));
                tc_address.setCellValueFactory(new PropertyValueFactory<>("address"));

                tv_customers.setItems(searchModelCustomerObservableList);
            }
        });

    }
}
