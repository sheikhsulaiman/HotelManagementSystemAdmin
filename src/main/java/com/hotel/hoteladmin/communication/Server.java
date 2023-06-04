package com.hotel.hoteladmin.communication;

import com.hotel.hoteladmin.DButils.DButils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Server extends Thread{

    public Server(Socket stk){
        this.stk = stk;
    }
    Socket stk;

    public void run(){
        try {
            DataInputStream din = new DataInputStream(stk.getInputStream());
            DataOutputStream dout = new DataOutputStream(stk.getOutputStream());
            String str = "";
            String[] list;
            //StringBuilder s1;
            do{
                str = (String) din.readUTF();
                list = str.split(",");

                switch (list[0]){
                    case "logInRequest":
                        //System.out.println(Arrays.toString(list));
                        //System.out.println(DButils.getUserExistance(list[1],list[2]));
                        str=DButils.getUserExistence(list[1],list[2]);
                        break;
                    case "getBookingsOf":
                        str = DButils.getBookingsOf(list[1]);
                        break;
                    case "getRooms":
                        str = DButils.getRoomsList();
                        break;
                    case "newUserRegistration":
                        DButils.register(list[1],list[2],list[3], Integer.parseInt(list[4]),list[5],list[6],list[7]);
                        str= String.valueOf(DButils.getLastUserId());
                        System.out.println(str);
                        break;
                    case "newBooking":
                        DButils.newBooking(Integer.parseInt(list[1]),Integer.parseInt(list[2]),list[3],list[4],list[5],list[6],list[7],list[8],list[9]);
                        str = "true";
                        break;
                    case "getCalendar":
                        str = DButils.getCalendar();
                        break;
                }
                //s1 = new StringBuilder(str);
                //s1.reverse();
                //str = s1.toString();
                dout.writeUTF(str);
                dout.flush();
//                if(str.equals("pots")){
//                    System.out.println("Connection lost from one of the client");
//                }
            }while (true);
        }catch (IOException e){}
    }
}
