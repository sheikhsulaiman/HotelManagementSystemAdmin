package com.hotel.hoteladmin.communication;

import com.hotel.hoteladmin.DButils.DButils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                        str=DButils.getUserExistance(list[1],list[2]);
                        break;
                    case "getBookingsOf":
                        str = DButils.getBookingsOf(list[1]);
                        break;
                    case "getRooms":
                        str = DButils.getRoomsList();
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
