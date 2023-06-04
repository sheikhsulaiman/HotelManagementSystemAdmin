package com.hotel.hoteladmin.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer extends Thread{
    public void run(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server rms;
        do{
            Socket stk = null;
            try {
                assert ss != null;
                stk = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            rms = new Server(stk);
            rms.start();
            System.out.println("One Client Connected");
        }while (true);
    }
}
