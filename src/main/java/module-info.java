module com.hotel.hoteladmin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
            
                            
    opens com.hotel.hoteladmin to javafx.fxml;
    exports com.hotel.hoteladmin;
    exports com.hotel.hoteladmin.controllers;
    opens com.hotel.hoteladmin.controllers to javafx.fxml;
}