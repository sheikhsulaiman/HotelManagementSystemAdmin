package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button btn_Resigter;

    @FXML
    private ChoiceBox<String> cb_gender;

    @FXML
    private TextArea ta_address;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_firstname;

    @FXML
    private TextField tf_lastname;

    @FXML
    private TextField tf_phone;

    @FXML
    private Button btn_back;

    private String defaultPassword = "1234";

    private String[] gender={"male","female"};

    public static int generatedId;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cb_gender.getItems().addAll(gender);

        btn_back.setOnAction(event -> SceneSwitcher.changeScene(event,"../booking.fxml","New Booking"));

        btn_Resigter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.register(tf_firstname.getText(),tf_lastname.getText(),Integer.parseInt(tf_phone.getText()),cb_gender.getValue(),tf_email.getText(), ta_address.getText());
                SceneSwitcher.changeScene(event,"../booking.fxml","New Booking");
            }
        });

    }
}
