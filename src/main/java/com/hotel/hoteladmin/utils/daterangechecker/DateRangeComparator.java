package com.hotel.hoteladmin.utils.daterangechecker;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class DateRangeComparator {
    public static boolean compare(DatePicker dp_in, DatePicker dp_out, String db_start, String db_end) {
        LocalDate startDate1 = dp_in.getValue();
        LocalDate endDate1 = dp_out.getValue();

        LocalDate startDate2 = LocalDate.parse(db_start);
        LocalDate endDate2 = LocalDate.parse(db_end);

        if (endDate1.isBefore(startDate2) || startDate1.isAfter(endDate2)) {
            return false;
        } else {
            return true;
        }
    }
}

