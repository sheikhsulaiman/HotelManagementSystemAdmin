package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private Button btn_newbooking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_newbooking.setOnAction(event -> SceneSwitcher.changeSceneToNewWindow("../booking.fxml","New Booking"));
    }
}
