package com.hotel.hoteladmin.utils.tables;

public class Bookings {
    private Integer roomNumber;
    private Integer userId;
    private Integer bookingId;
    public String checkIn,checkOut,payMethod,payStatus,roomService,carParking,poolAccess;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
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

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRoomService() {
        return roomService;
    }

    public void setRoomService(String roomService) {
        this.roomService = roomService;
    }

    public String getCarParking() {
        return carParking;
    }

    public void setCarParking(String carParking) {
        this.carParking = carParking;
    }

    public String getPoolAccess() {
        return poolAccess;
    }

    public void setPoolAccess(String poolAccess) {
        this.poolAccess = poolAccess;
    }

    public Bookings(Integer bookingId, Integer roomNumber, Integer userId, String checkIn, String checkOut, String payMethod, String payStatus, String roomService, String carParking, String poolAccess) {
        this.bookingId = bookingId;
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.payMethod = payMethod;
        this.payStatus = payStatus;
        this.roomService = roomService;
        this.carParking = carParking;
        this.poolAccess = poolAccess;
    }
}
