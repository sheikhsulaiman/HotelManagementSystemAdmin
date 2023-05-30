package com.hotel.hoteladmin.utils;

import com.hotel.hoteladmin.utils.tables.Bookings;
import com.hotel.hoteladmin.utils.tables.Customers;
import com.hotel.hoteladmin.utils.tables.Rooms;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Search {
    public static void bookingSearch(TextField tf_keyword, ObservableList<Bookings> searchModelBookingObservableList, TableView<Bookings> tableView){
        //initial filtered list
        FilteredList<Bookings> filteredData = new FilteredList<>(searchModelBookingObservableList, b->true);
        tf_keyword.textProperty().addListener((observable,oldValue,newValue )->{
            filteredData.setPredicate(searchModel -> {
                // If no search value then display all records or whatever records is currently have. no changes.
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (searchModel.getRoomNumber().toString().indexOf(searchKeyword)>-1){
                    return true; // Means we found a match in firstName
                }else if(searchModel.getUserId().toString().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getBookingId().toString().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getCheckIn().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getCheckOut().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getPayMethod().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else {
                    return false; // nothing to display
                }
            });
        });

        SortedList<Bookings> sortedData = new SortedList<>(filteredData);

        //Bind sorted data to the table
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        //Apply filtered and sorted data to the table view
        tableView.setItems(sortedData);
    }
    public static void customerSearch(TextField tf_keyword, ObservableList<Customers> searchModelCustomerObservableList, TableView<Customers> tableView){
        //initial filtered list
        FilteredList<Customers> filteredData = new FilteredList<>(searchModelCustomerObservableList, b->true);
        tf_keyword.textProperty().addListener((observable,oldValue,newValue )->{
            filteredData.setPredicate(searchModel -> {
                // If no search value then display all records or whatever records is currently have. no changes.
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (searchModel.getUserId().toString().indexOf(searchKeyword)>-1){
                    return true; // Means we found a match in firstName
                }else if(searchModel.getFirstName().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getLastName().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getPhone().toString().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getGender().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getEmail().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getAddress().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getPassword().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else {
                    return false; // nothing to display
                }
            });
        });

        SortedList<Customers> sortedData = new SortedList<>(filteredData);

        //Bind sorted data to the table
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        //Apply filtered and sorted data to the table view
        tableView.setItems(sortedData);
    }
    public static void roomSearch(TextField tf_keyword, ObservableList<Rooms> searchModelRoomObservableList, TableView<Rooms> tableView){
        //initial filtered list
        FilteredList<Rooms> filteredData = new FilteredList<>(searchModelRoomObservableList, b->true);
        tf_keyword.textProperty().addListener((observable,oldValue,newValue )->{
            filteredData.setPredicate(searchModel -> {
                // If no search value then display all records or whatever records is currently have. no changes.
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (searchModel.getRoomNumber().toString().indexOf(searchKeyword)>-1){
                    return true; // Means we found a match in firstName
                }else if(searchModel.getRoomNumber().toString().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else if(searchModel.getRoomType().toLowerCase().indexOf(searchKeyword)>-1){
                    return true; // means we found a match in lastname
                }else {
                    return false; // nothing to display
                }
            });
        });

        SortedList<Rooms> sortedData = new SortedList<>(filteredData);

        //Bind sorted data to the table
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        //Apply filtered and sorted data to the table view
        tableView.setItems(sortedData);
    }
}
