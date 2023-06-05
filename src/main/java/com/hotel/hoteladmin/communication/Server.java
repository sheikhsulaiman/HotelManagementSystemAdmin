package com.hotel.hoteladmin.communication;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.encryption.Encrypt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Server extends Thread{

    public Server(Socket stk){
        this.stk = stk;
    }
    Socket stk;

    public void run(){
        Encrypt encrypt = new Encrypt();
        encrypt.setAlgorithm("md5");
        try {
            DataInputStream din = new DataInputStream(stk.getInputStream());
            DataOutputStream dout = new DataOutputStream(stk.getOutputStream());
            String str = "";
            String[] list;
            //StringBuilder s1;
            do{
                str = (String) din.readUTF();
                list = str.split("~");

                switch (list[0]){
                    case "logInRequest":
                        System.out.println(Arrays.toString(list));
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
                    case "getInvoice":
                        str = Integer.toString(DButils.getInvoiceId(Integer.parseInt(list[1])));
                        break;
                    case "createNewInvoice":
                        DButils.createNewInvoice(Integer.parseInt(list[1]) ,Integer.parseInt(list[2]) ,list[3]);
                        str="true";
                        break;
                    case "getBookingDetailsForClient":
                        str = DButils.getBookingDetailsForClient(DButils.getLastBookingId());
                        break;
                    case "updateProfile":
                        for (String data:
                             list) {
                            System.out.println(data);
                        }
                        str=DButils.updateProfile(list[1],list[2],Integer.parseInt(list[3]),list[4],list[5],list[6],Integer.parseInt(list[7]),list.length<9?DButils.getPassword(list[7]):encrypt.encrypt(list[8]));
                        break;
                    case "deleteBooking":
                        DButils.deleteBooking(Integer.parseInt(list[1]));
                        str = "true";
                        break;
                    case "updateBooking":
                        DButils.updateBooking(Integer.parseInt(list[1]),Integer.parseInt(list[2]),Integer.parseInt(list[3]),list[4],list[5],list[6],list[7],list[8],list[9],list[10]);
                        break;
                    case "updateMoneyVault":
                        DButils.updateMoneyVault(Integer.parseInt(list[1]),list[2]);
                        break;
                    case "updateInvoice":
                        DButils.updateInvoice(Integer.parseInt(list[1]),Integer.parseInt(list[2]),list[3]);
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
