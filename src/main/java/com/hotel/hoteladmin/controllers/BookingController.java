package com.hotel.hoteladmin.controllers;

import com.hotel.hoteladmin.DButils.DataBaseConnection;
import com.hotel.hoteladmin.utils.SceneSwitcher;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tf_user_id.setText(Integer.toString( SignUpController.generatedId));



        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        String connectQuery = "SELECT DISTINCT type FROM rooms";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(connectQuery);

            String type="";
            while (resultSet.next()){
                type = resultSet.getString("type");

                cb_roomType.getItems().add(type);
            }


        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }

        // new user scene switch
        btn_newuser.setOnAction(event -> SceneSwitcher.changeSceneToNewWindow("../signup.fxml","New Registration"));
    }
}