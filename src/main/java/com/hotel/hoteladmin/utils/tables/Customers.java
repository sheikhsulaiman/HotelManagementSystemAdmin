package com.hotel.hoteladmin.utils.tables;

public class Customers {
    private String firstName,lastName,gender,email,address,password;
    private Integer phone,userId;

    public Customers(Integer userId,String password, String firstName, String lastName, String gender, Integer phone, String email, String address) {
        this.password = password;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getUserId(){return userId;}

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUserId(Integer userId){this.userId=userId;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
