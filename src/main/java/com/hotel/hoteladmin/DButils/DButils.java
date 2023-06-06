package com.hotel.hoteladmin.DButils;

import com.hotel.hoteladmin.encryption.Encrypt;
import com.hotel.hoteladmin.utils.daterangechecker.DateRangeComparator;
import com.hotel.hoteladmin.utils.tables.Bookings;
import com.hotel.hoteladmin.utils.tables.Customers;
import com.hotel.hoteladmin.utils.tables.Rooms;
import com.hotel.hoteladmin.utils.tables.RoomsDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

import java.sql.*;
import java.util.ArrayList;

public class DButils {
    public static void register(String firstname, String lastname, int phone, String gender, String email, String address){
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
    public static String updateProfile(String firstname, String lastname, int phone, String gender, String email, String address,int id,String password){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        try {
            PreparedStatement updateBookingStatement = connectDB.prepareStatement("UPDATE users SET firstname=?,lastname=?,phone=?,gender=?,email=?,address=?,password=? WHERE id=?");

            updateBookingStatement.setString(1,firstname);
            updateBookingStatement.setString(2, lastname);
            updateBookingStatement.setInt(3,phone);
            updateBookingStatement.setString(4, gender);
            updateBookingStatement.setString(5, email);
            updateBookingStatement.setString(6, address);
            updateBookingStatement.setString(7,password);
            updateBookingStatement.setInt(8, id);

            updateBookingStatement.executeUpdate();
            connectDB.close();
            return "true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
            //System.out.println("SQL Exception");
        }
    }
    public static void register(String firstname, String lastname,String password, int phone, String gender, String email, String address){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        Encrypt encrypt = new Encrypt();
        encrypt.setAlgorithm("md5");
        String pass = encrypt.encrypt(password);
        //String output="";
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
//    public static void newBooking(int roomNo,int userId,String checkInDate,String checkOutDate,String payType,String roomService,String poolAccess,String carParking) {
//        DataBaseConnection dbConnection = new DataBaseConnection();
//        Connection connectDB = dbConnection.getDatabaseLink();
//        try {
//            PreparedStatement updateBookingStatement = connectDB.prepareStatement("insert into bookings(roomno, userid, checkin, checkout, paymentmethod, paymentstatus, roomservice, poolaccess, carparking) values (?,?,?,?,?,?,?,?,?)");
//            PreparedStatement updateCalenderStatement = connectDB.prepareStatement("insert into calender(roomid,userid,start,end,bookingid) values (?,?,?,?,?)");
//            //PreparedStatement updateStatement = connectDB.prepareStatement("UPDATE rooms SET status='booked' WHERE number=?");
//
//            //updateStatement.setInt(1,roomNo);
//            updateBookingStatement.setInt(1, roomNo);
//            updateCalenderStatement.setInt(1, roomNo);
//            updateBookingStatement.setInt(2, userId);
//            updateCalenderStatement.setInt(2, userId);
//
//            updateBookingStatement.setString(3, checkInDate);
//            updateCalenderStatement.setString(3, checkInDate);
//            updateBookingStatement.setString(4, checkOutDate);
//            updateCalenderStatement.setString(4, checkOutDate);
//            updateBookingStatement.setString(5, payType);
//            updateBookingStatement.setString(6, "Unpaid");
//            updateBookingStatement.setString(7, roomService);
//            updateBookingStatement.setString(8, poolAccess);
//            updateBookingStatement.setString(9, carParking);
//
//            //updateStatement.executeUpdate();
//            updateBookingStatement.executeUpdate();
//            int bookingId = (DButils.getLastBookingId());
//            updateCalenderStatement.setInt(5, bookingId);
//            updateCalenderStatement.executeUpdate();
//            connectDB.close();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            //System.out.println("SQL Exception");
//        }
//
//    }

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
            connectDB.close();
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
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
            connectDB.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            connectDB.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchModelObservableList;

    }

    public static int getInvoiceId(int bookingId){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        int id=0;
        try{
            PreparedStatement getIdStatement = connectDB.prepareStatement("SELECT invoiceno FROM moneyVault WHERE bookingid=?");
            getIdStatement.setInt(1,bookingId);
            ResultSet resultSet= getIdStatement.executeQuery();
            while (resultSet.next()){
                id=resultSet.getInt(1);
            }
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void createNewInvoice(int bookingId,int cost,String status){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();
        try{
            PreparedStatement updateInvoiceStatement = connectDB.prepareStatement("INSERT INTO moneyVault(bookingid,cost,status) VALUES(?,?,?) ");
            updateInvoiceStatement.setInt(1,bookingId);
            updateInvoiceStatement.setInt(2,cost);
            updateInvoiceStatement.setString(3,status);
            updateInvoiceStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateInvoice(int bookingId,int cost,String status){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();
        try{
            PreparedStatement updateInvoceStatement = connectDB.prepareStatement("UPDATE moneyVault SET cost=?,status=? WHERE bookingid=?");
            updateInvoceStatement.setInt(3,bookingId);
            updateInvoceStatement.setInt(1,cost);
            updateInvoceStatement.setString(2,status);
            updateInvoceStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public static String getUserName(int userId){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        String firstname = "",lastname="";

        try{
            PreparedStatement getNameStatement = connectDB.prepareStatement("SELECT firstname,lastname FROM users WHERE id=?");
            getNameStatement.setInt(1,userId);
            ResultSet resultSet = getNameStatement.executeQuery();
            while (resultSet.next()){
                firstname = resultSet.getString("firstname");
                lastname = resultSet.getString("lastname");
            }
            connectDB.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return firstname+" "+lastname;
    }

    public static String getUserExistence(String userId, String password){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        ResultSet resultSet=null ;
        Encrypt encrypt = new Encrypt();
        encrypt.setAlgorithm("md5");
        String pass = encrypt.encrypt(password);
        String output="";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement("SELECT * from users WHERE id=? AND password = ?");
            preparedStatement.setInt(1,Integer.parseInt(userId));
            preparedStatement.setString(2,pass);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                output = output+(resultSet.getInt(1));
                output = output+"~"+resultSet.getString(2);
                output = output+"~"+resultSet.getString(3);
                output = output+"~"+resultSet.getInt(4);
                output = output+"~"+resultSet.getString(5);
                output = output+"~"+resultSet.getString(6);
                output = output+"~"+resultSet.getString(7);
            }else {
                output = "false";
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static int getBalance(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        ResultSet resultSet=null ;
        int balance=0;
        try {
            Statement getBalanceStatement = connectDB.createStatement();
            resultSet = getBalanceStatement.executeQuery("SELECT SUM(cost) FROM moneyVault WHERE status='Paid'");
            if(resultSet.next()){
                balance=resultSet.getInt("SUM(cost)");
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
    public static String getBookingsOf(String userId){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        ResultSet resultSet=null ;
        String output="";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement("SELECT * from bookings WHERE userid=?");
            preparedStatement.setInt(1,Integer.parseInt(userId));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                output = output+(resultSet.getInt(1));
                output = output+"~"+resultSet.getInt(2);
                output = output+"~"+resultSet.getInt(3);
                output = output+"~"+resultSet.getString(4);
                output = output+"~"+resultSet.getString(5);
                output = output+"~"+resultSet.getString(6);
                output = output+"~"+resultSet.getString(7);
                output = output+"~"+resultSet.getString(8);
                output = output+"~"+resultSet.getString(9);
                output = output+"~"+resultSet.getString(10);

                output = output+":";
            }
            int lastIndex = output.lastIndexOf(':');
            if(lastIndex!=(-1))
                output = output.substring(0,lastIndex);
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    public static String getCalendar(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        ResultSet resultSet=null ;
        String output="";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement("select id,roomid,start,end from calender");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                output = output+(resultSet.getInt(1));
                output = output+"~"+resultSet.getInt(2);
                output = output+"~"+resultSet.getString(3);
                output = output+"~"+resultSet.getString(4);

                output = output+":";
            }
            int lastIndex = output.lastIndexOf(':');
            if(lastIndex!=(-1)) {
                output = output.substring(0, lastIndex);
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    public static String getRoomsList(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        ResultSet resultSet=null ;
        String output="";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement("SELECT * from rooms");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                output = output+(resultSet.getInt(1));
                output = output+"~"+resultSet.getString(2);

                output = output+":";
            }
            int lastIndex = output.lastIndexOf(':');
            output = output.substring(0,lastIndex);
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
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
    public static String getBookingDetailsForClient(Integer bookingid){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        //ArrayList<String> list = new ArrayList<>(10);
        String output="";
        try{
            PreparedStatement getStm =connectDB.prepareStatement( "SELECT * FROM bookings WHERE bookingid=?");
            getStm.setInt(1,bookingid);
            ResultSet resultSet = getStm.executeQuery();
            while (resultSet.next()){
                output = output+(resultSet.getInt(1));
                output = output+="~"+resultSet.getInt(2);
                output = output+="~"+resultSet.getInt(3);
                output = output+="~"+resultSet.getString(4);
                output = output+="~"+resultSet.getString(5);
                output = output+="~"+resultSet.getString(6);
                output = output+="~"+resultSet.getString(7);
                output = output+="~"+resultSet.getString(8);
                output = output+="~"+resultSet.getString(9);
                output = output+="~"+resultSet.getString(10);

                output = output+":";
            }
            int lastIndex = output.lastIndexOf(':');
            if(lastIndex!=(-1))
                output = output.substring(0,lastIndex);
            connectDB.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return output;
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
    public static String getPassword(String userId){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        String pass="";

        try {
            PreparedStatement getPasswordStatement = connectDB.prepareStatement("SELECT password from users WHERE id=?");
            getPasswordStatement.setInt(1,Integer.parseInt(userId));
            ResultSet resultSet = getPasswordStatement.executeQuery();
            if(resultSet.next())
                pass=resultSet.getString("password");
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println("SQL Exception");
        }
        return pass;
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

    public static void updateMoneyVault(int bookingId,String status){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        try{
            PreparedStatement updateCostStatement = connectDB.prepareStatement("UPDATE moneyVault SET status=? WHERE bookingid=?;");
            updateCostStatement.setInt(2,bookingId);
            updateCostStatement.setString(1,status);
            updateCostStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<RoomsDetails> getSelectedRoomDetails(int selectedRoomNumber) {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();
        ObservableList<RoomsDetails> searchModelObservableList = null;
        try {
            PreparedStatement statement = connectDB.prepareStatement("SELECT bookingid,userid,start,end FROM calender WHERE roomid=?");
            statement.setInt(1, selectedRoomNumber);
            searchModelObservableList = FXCollections.observableArrayList();
            ResultSet queryOutput = statement.executeQuery();
            while (queryOutput.next()) {
                Integer queryBookingId = queryOutput.getInt("bookingid");
                Integer queryUserId = queryOutput.getInt("userid");
                String queryCheckIn = queryOutput.getString("start");
                String queryCheckOut = queryOutput.getString("end");
                searchModelObservableList.add(new RoomsDetails(queryBookingId, queryUserId, queryCheckIn, queryCheckOut));
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchModelObservableList;
    }
}
