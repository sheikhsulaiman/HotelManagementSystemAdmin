package com.hotel.hoteladmin.DButils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection implements DataBase{
    public Connection databaseLink;
    public Connection getDatabaseLink(){
        String url = "jdbc:sqlite:src/main/java/com/hotel/hoteladmin/DButils/hotel.db";
        try{
            databaseLink = DriverManager.getConnection(url);
        } catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
