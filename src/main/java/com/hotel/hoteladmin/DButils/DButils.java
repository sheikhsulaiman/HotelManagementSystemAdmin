package com.hotel.hoteladmin.DButils;

import java.sql.*;
import java.util.ArrayList;

public class DButils {
    public static void resigterByAdmin(String firstname,String lastname,int phone,String gender,String email,String address){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try {
            PreparedStatement insertStatement = connectDB.prepareStatement("INSERT INTO users (firstname, lastname, phone, gender, email, address, password) VALUES (?,?,?,?,?,?,?)");

            insertStatement.setString(1,firstname);
            insertStatement.setString(2, lastname);
            insertStatement.setInt(3,phone);
            insertStatement.setString(4, gender);
            insertStatement.setString(5, email);
            insertStatement.setString(6, address);
            insertStatement.setString(7, "1234");

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }
    }

    public static ArrayList<String> getVacentRooms(String roomType){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        ArrayList<String> list = new ArrayList<>(9);

//        String connectQuery = "SELECT DISTINCT type FROM rooms";
        try {
            PreparedStatement getRoomsStm = connectDB.prepareStatement("select number from rooms where type=? and status='vacant'");
            getRoomsStm.setString(1,roomType);
            ResultSet resultSet = getRoomsStm.executeQuery();
            while (resultSet.next()){
                list.add(Integer.toString(resultSet.getInt("number")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return list;
    }

    public static ArrayList<String> getRoomType(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        String connectQuery = "SELECT DISTINCT type FROM rooms";
        ArrayList<String> list = new ArrayList<>(3);

        try{
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(connectQuery);
            while (resultSet.next()){
                list.add(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return list;
    }

    public static int getLastUserId(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        int id=0;

        try {
            Statement getLastIdStatement = connectDB.createStatement();
            ResultSet resultSet = getLastIdStatement.executeQuery("SELECT id FROM users WHERE ROWID IN ( SELECT max( ROWID ) FROM users)" );

                id=resultSet.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println("SQL Exception");
        }
        return id;
    }
}
