package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button btn_logIn;

    @FXML
    private Label l_message;

    @FXML
    private PasswordField pf_password;

    @FXML
    private TextField tf_userName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(DButils.resolveAdminLogin(tf_userName.getText(),pf_password.getText())){
                    SceneSwitcher.changeScene(event,"../app-main.fxml","Hotel Admin");
                }else {
                    l_message.setText("Wrong Credential");
                }
            }
        });
    }
}
