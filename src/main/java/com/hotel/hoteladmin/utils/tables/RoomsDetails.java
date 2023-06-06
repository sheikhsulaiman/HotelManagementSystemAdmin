package com.hotel.hoteladmin.utils.tables;

public class RoomsDetails {
    Integer bookingId,userId;
    String checkIn, checkOut;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public RoomsDetails(Integer bookingId, Integer userId, String checkIn, String checkout) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.checkIn = checkIn;
        this.checkOut = checkout;
    }
}
