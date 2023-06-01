package com.hotel.hoteladmin.DButils;

import com.hotel.hoteladmin.encryption.Encrypt;
import com.hotel.hoteladmin.utils.daterangechecker.DateRangeComparator;
import com.hotel.hoteladmin.utils.tables.Bookings;
import com.hotel.hoteladmin.utils.tables.Customers;
import com.hotel.hoteladmin.utils.tables.Rooms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import org.sqlite.core.DB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DButils {
    public static void resigterByAdmin(String firstname,String lastname,int phone,String gender,String email,String address){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        Encrypt encrypt = new Encrypt();
        encrypt.setAlgorithm("md5");
        String pass = encrypt.encrypt("1234");
        try {
            PreparedStatement updateBookingStatement = connectDB.prepareStatement("INSERT INTO users (firstname, lastname, phone, gender, email, address, password) VALUES (?,?,?,?,?,?,?)");

            updateBookingStatement.setString(1,firstname);
            updateBookingStatement.setString(2, lastname);
            updateBookingStatement.setInt(3,phone);
            updateBookingStatement.setString(4, gender);
            updateBookingStatement.setString(5, email);
            updateBookingStatement.setString(6, address);
            updateBookingStatement.setString(7, pass);

            updateBookingStatement.executeUpdate();
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
            PreparedStatement updateBookingStatement = connectDB.prepareStatement("insert into bookings(roomno, userid, checkin, checkout, paymentmethod, paymentstatus, roomservice, poolaccess, carparking) values (?,?,?,?,?,?,?,?,?)");
            PreparedStatement updateCalenderStatement = connectDB.prepareStatement("insert into calender(roomid,userid,start,end,bookingid) values (?,?,?,?,?)");
            //PreparedStatement updateStatement = connectDB.prepareStatement("UPDATE rooms SET status='booked' WHERE number=?");

            //updateStatement.setInt(1,roomNo);
            updateBookingStatement.setInt(1,roomNo);
            updateCalenderStatement.setInt(1,roomNo);
            updateBookingStatement.setInt(2,userId);
            updateCalenderStatement.setInt(2,userId);

            updateBookingStatement.setString(3,checkInDate);
            updateCalenderStatement.setString(3,checkInDate);
            updateBookingStatement.setString(4, checkOutDate);
            updateCalenderStatement.setString(4, checkOutDate);
            updateBookingStatement.setString(5, payType);
            updateBookingStatement.setString(6, payStatus);
            updateBookingStatement.setString(7, roomService);
            updateBookingStatement.setString(8, poolAccess);
            updateBookingStatement.setString(9, carParking);

            //updateStatement.executeUpdate();
            updateBookingStatement.executeUpdate();
            int bookingId = (DButils.getLastBookingId());
            updateCalenderStatement.setInt(5,bookingId);
            updateCalenderStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }

    }

    public static void deleteBooking(int bookingId){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try{
            PreparedStatement deleteFromBookingTableStatement = connectDB.prepareStatement("DELETE FROM bookings WHERE bookingid = ?");
            PreparedStatement deleteFromCalenderTableStatement = connectDB.prepareStatement("DELETE FROM calender WHERE bookingid = ?");
            deleteFromBookingTableStatement.setInt(1,bookingId);
            deleteFromCalenderTableStatement.setInt(1,bookingId);
            deleteFromCalenderTableStatement.executeUpdate();
            deleteFromBookingTableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateBooking(int bookingId,int roomNo,int userId,String checkInDate,String checkOutDate,String payType,String payStatus,String roomService,String poolAccess,String carParking){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try {
            PreparedStatement updateBookingStatement = connectDB.prepareStatement("UPDATE bookings SET roomno = ?, userid = ?, checkin = ?, checkout = ?,paymentmethod = ?, paymentstatus = ?, roomservice = ?, poolaccess = ?, carparking = ? WHERE (bookingid=?)");
            PreparedStatement updateCalenderStatement = connectDB.prepareStatement("update calender set roomid=?,userid=?,start=?,end=? where (bookingid=?)");
            //PreparedStatement updateStatement = connectDB.prepareStatement("UPDATE rooms SET status='booked' WHERE number=?");

            //updateStatement.setInt(1,roomNo);
            updateBookingStatement.setInt(1,roomNo);
            updateCalenderStatement.setInt(1,roomNo);
            updateBookingStatement.setInt(2,userId);
            updateCalenderStatement.setInt(2,userId);
            updateCalenderStatement.setInt(5,bookingId);
            updateBookingStatement.setString(3,checkInDate);
            updateCalenderStatement.setString(3,checkInDate);
            updateBookingStatement.setString(4, checkOutDate);
            updateCalenderStatement.setString(4, checkOutDate);
            updateBookingStatement.setString(5, payType);
            updateBookingStatement.setString(6, payStatus);
            updateBookingStatement.setString(7, roomService);
            updateBookingStatement.setString(8, poolAccess);
            updateBookingStatement.setString(9, carParking);
            updateBookingStatement.setInt(10, bookingId);

            //updateStatement.executeUpdate();
            updateBookingStatement.executeUpdate();
            updateCalenderStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }

    }
    public static void makeRoomVacant(int roomNo){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try {
            PreparedStatement updateStatement = connectDB.prepareStatement("UPDATE rooms SET status='vacant' WHERE number=?");

            updateStatement.setInt(1,roomNo);


            updateStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }

    }

    public static ObservableList<Customers> getCustomerTable() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        String userDetailsViewQuery = "SELECT id,firstname,lastname,gender,phone,email,address,password FROM users";
        ObservableList<Customers> searchModelObservableList = FXCollections.observableArrayList();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userDetailsViewQuery);
            while (queryOutput.next()) {
                Integer queryUserId = queryOutput.getInt("id");
                String queryPassword = queryOutput.getString("password");
                String queryFirstname = queryOutput.getString("firstname");
                String queryLastname = queryOutput.getString("lastname");
                Integer queryPhone = queryOutput.getInt("phone");
                String queryGender = queryOutput.getString("gender");
                String queryEmail = queryOutput.getString("email");
                String queryAddress = queryOutput.getString("address");

                searchModelObservableList.add(new Customers(queryUserId,queryPassword,queryFirstname, queryLastname, queryGender, queryPhone, queryEmail, queryAddress));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return searchModelObservableList;

    }
    public static ObservableList<Bookings> getBookingTable() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        String userDetailsViewQuery = "SELECT * FROM bookings";
        ObservableList<Bookings> searchModelObservableList = FXCollections.observableArrayList();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userDetailsViewQuery);
            while (queryOutput.next()) {
                Integer queryBookingId = queryOutput.getInt("bookingid");
                Integer queryRoomNo = queryOutput.getInt("roomno");
                Integer queryUserid = queryOutput.getInt("userid");
                String queryCheckIn = queryOutput.getString("checkin");
                String queryCheckOut = queryOutput.getString("checkout");
                String queryPayMethod = queryOutput.getString("paymentmethod");
                String queryPayStatus = queryOutput.getString("paymentstatus");
                String queryRoomService = queryOutput.getString("roomservice");
                String queryPoolAccess = queryOutput.getString("poolaccess");
                String queryCarParking = queryOutput.getString("carparking");


                searchModelObservableList.add(new Bookings(queryBookingId,queryRoomNo,queryUserid,queryCheckIn,queryCheckOut,queryPayMethod,queryPayStatus,queryRoomService,queryCarParking,queryPoolAccess));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return searchModelObservableList;

    }
    public static ObservableList<Rooms> getRoomsTable() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        String userDetailsViewQuery = "SELECT number,type FROM rooms";
        ObservableList<Rooms> searchModelObservableList = FXCollections.observableArrayList();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userDetailsViewQuery);
            while (queryOutput.next()) {
                Integer queryRoomNumber = queryOutput.getInt("number");
                String queryRoomType = queryOutput.getString("type");
                searchModelObservableList.add(new Rooms(queryRoomNumber, queryRoomType));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return searchModelObservableList;

    }

        public static ArrayList<String> getRooms(String roomType){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        ArrayList<String> list = new ArrayList<>(9);

//        String connectQuery = "SELECT DISTINCT type FROM rooms";
        try {
            PreparedStatement getAllRooms = connectDB.prepareStatement("select number from rooms");
            PreparedStatement getRoomsStm = connectDB.prepareStatement("select number from rooms where type=?");
            getRoomsStm.setString(1,roomType);
            ResultSet resultSet;
            if(roomType.equals("any")){resultSet= getAllRooms.executeQuery();}
            else {resultSet = getRoomsStm.executeQuery(); }
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
    public static ArrayList<String> getBookingDetails(Integer bookingid){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        ArrayList<String> list = new ArrayList<>(10);

        try{
            PreparedStatement getStm =connectDB.prepareStatement( "SELECT * FROM bookings WHERE bookingid=?");
            getStm.setInt(1,bookingid);
            ResultSet resultSet = getStm.executeQuery();
            while (resultSet.next()){
                list.add(Integer.toString(resultSet.getInt(2)));
                list.add(Integer.toString(resultSet.getInt(3)));
                list.add((resultSet.getString(4)));
                list.add((resultSet.getString(5)));
                list.add((resultSet.getString(6)));
                list.add((resultSet.getString(7)));
                list.add((resultSet.getString(8)));
                list.add((resultSet.getString(9)));
                list.add((resultSet.getString(10)));
                list.add((Integer.toString(resultSet.getInt("bookingid"))));
            }
            connectDB.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return list;
    }
    public static String getRoomType(String roomNo){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        String roomType="";

        try{
            PreparedStatement getRoomTypeStm = connectDB.prepareStatement( "SELECT type FROM rooms WHERE number=?");
            getRoomTypeStm.setInt(1,Integer.parseInt(roomNo));
            ResultSet resultSet = getRoomTypeStm.executeQuery();
            while (resultSet.next()){
                roomType = (resultSet.getString("type"));
            }
            connectDB.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return roomType;
    }

    public static boolean resolveAdminLogin(String userId,String password){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        try {
            PreparedStatement logInStatement = connectDB.prepareStatement("SELECT * FROM admin WHERE id=? AND password=?");
            Encrypt encrypt = new Encrypt();
            encrypt.setAlgorithm("md5");
            String encrypted_password  = encrypt.encrypt(password);
            int intUserId;
                    try{
                        intUserId = Integer.parseInt(userId);
                    }catch (NumberFormatException e){intUserId = 0;}
            logInStatement.setInt(1,intUserId);
            logInStatement.setString(2,encrypted_password);
            ResultSet resultSet = logInStatement.executeQuery();
            connectDB.close();
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
    public static int getLastBookingId(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        int id=0;

        try {
            Statement getLastIdStatement = connectDB.createStatement();
            ResultSet resultSet = getLastIdStatement.executeQuery("SELECT bookingid FROM bookings WHERE ROWID IN ( SELECT max( ROWID ) FROM bookings)" );

                id=resultSet.getInt("bookingid");
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println("SQL Exception");
        }
        return id;
    }

    public static ArrayList<String> getBookedRooms(DatePicker dp_checkin,DatePicker dp_checkout){
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connectDB = dataBaseConnection.getDatabaseLink();
        ArrayList<String> list = new ArrayList<>(9);

        try {
            PreparedStatement getDates = connectDB.prepareStatement("SELECT * FROM calender");
            ResultSet resultSet = getDates.executeQuery();

            String db_start,db_end;
            int roomid;
            while (resultSet.next()){
                db_start=(resultSet.getString("start"));
                db_end=(resultSet.getString("end"));
                roomid=resultSet.getInt("roomid");
                if(DateRangeComparator.compare(dp_checkin,dp_checkout,db_start,db_end)){
                    list.add(Integer.toString(roomid));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                connectDB.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
        }
        return list;
    }
}
