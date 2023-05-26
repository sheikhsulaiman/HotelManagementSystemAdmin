package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.DButils.DataBaseConnection;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private ChoiceBox<String> cb_roomType;
    @FXML
    private Button btn_newuser;
    @FXML
    private TextField tf_user_id;

    @FXML
    private Button btn_lastUserId;

    @FXML
    private ChoiceBox<String> cb_roomNo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_lastUserId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tf_user_id.setText(Integer.toString(DButils.getLastUserId()));
            }
        });

        cb_roomType.getItems().addAll(DButils.getRoomType());

        // new user scene switch
        btn_newuser.setOnAction(event -> SceneSwitcher.changeSceneToNewWindow("../signup.fxml","New Registration"));
    }
}