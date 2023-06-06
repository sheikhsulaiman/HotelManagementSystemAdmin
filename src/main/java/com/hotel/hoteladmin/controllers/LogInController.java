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
import javafx.scene.input.MouseEvent;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            l_ipAddress.setText("IP: " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            l_ipAddress.setText("Ip not found.");
        }
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
