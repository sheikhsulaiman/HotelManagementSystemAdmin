package com.hotel.hoteladmin.DButils;

import com.hotel.hoteladmin.utils.tables.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }
    }

    public static void newBooking(int roomNo,int userId,String checkInDate,String checkOutDate,String payType,String payStatus,String roomService,String poolAccess,String carParking){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try {
            PreparedStatement insertStatement = connectDB.prepareStatement("insert into bookings(roomno, userid, checkin, checkout, paymentmethod, paymentstatus, roomservice, poolaccess, carparking) values (?,?,?,?,?,?,?,?,?);");
            PreparedStatement updateStatement = connectDB.prepareStatement("UPDATE rooms SET status='booked' WHERE number=?");

            updateStatement.setInt(1,roomNo);
            insertStatement.setInt(1,roomNo);
            insertStatement.setInt(2,userId);
            insertStatement.setString(3,checkInDate);
            insertStatement.setString(4, checkOutDate);
            insertStatement.setString(5, payType);
            insertStatement.setString(6, payStatus);
            insertStatement.setString(7, roomService);
            insertStatement.setString(8, poolAccess);
            insertStatement.setString(9, carParking);

            updateStatement.executeUpdate();
            insertStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }

    }

    public static ObservableList<Customers> getCustomerTable() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        String userDetailsViewQuery = "SELECT firstname,lastname,gender,phone,email,address FROM users";
        ObservableList<Customers> searchModelObservableList = FXCollections.observableArrayList();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userDetailsViewQuery);
            while (queryOutput.next()) {
                String queryFirstname = queryOutput.getString("firstname");
                String queryLastname = queryOutput.getString("lastname");
                Integer queryPhone = queryOutput.getInt("phone");
                String queryGender = queryOutput.getString("gender");
                String queryEmail = queryOutput.getString("email");
                String queryAddress = queryOutput.getString("address");

                searchModelObservableList.add(new Customers(queryFirstname, queryLastname, queryGender, queryPhone, queryEmail, queryAddress));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchModelObservableList;

    }

        public static ArrayList<String> getVacantRooms(String roomType){
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
            connectDB.close();
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
            connectDB.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return list;
    }

    public static boolean resolveAdminLogin(String userId,String password){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        try {
            PreparedStatement logInStatement = connectDB.prepareStatement("SELECT * FROM admin WHERE id=? AND password=?");
            int intUserId;
                    try{
                        intUserId = Integer.parseInt(userId);
                    }catch (NumberFormatException e){intUserId = 0;}
            logInStatement.setInt(1,intUserId);
            logInStatement.setString(2,password);
            ResultSet resultSet = logInStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getLastUserId(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        int id=0;

        try {
            Statement getLastIdStatement = connectDB.createStatement();
            ResultSet resultSet = getLastIdStatement.executeQuery("SELECT id FROM users WHERE ROWID IN ( SELECT max( ROWID ) FROM users)" );

                id=resultSet.getInt("id");
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println("SQL Exception");
        }
        return id;
    }
}
