package com.hotel.hoteladmin.utils.pricechart;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class PriceChart {


    public static int calculatePrice(String roomType,LocalDate checkIn,LocalDate checkOut,String roomService,String carParking,String poolAccess){
        int singleBed = 25;
        int doubleBed = 40;
        int multiBed = 59;

        int roomServicePrice= 5;
        int carParkingPrice = 4;
        int poolAccessPrice = 7;

        Integer sum = 0;
        int totaldays = Period.between(checkOut,checkIn).getDays();
        if(totaldays<0){
            totaldays = totaldays*-1;
        }
        totaldays=totaldays+1;
        if(roomType.equals("single")){
            sum = totaldays*singleBed;
        } else if (roomType.equals("double")) {
            sum = totaldays*doubleBed;
        }else if(roomType.equals("multi")){
            sum = totaldays*multiBed;
        }else{
            sum=0;
        }

        if(roomService.equals("YES")){
            sum+=roomServicePrice;
        }
        if(carParking.equals("YES")){
            sum+=carParkingPrice;
        }
        if(poolAccess.equals("YES")){
            sum+=poolAccessPrice;
        }
        return sum;
    }
}
