package zad1.MainServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class NewLangListener extends Thread{
    MainServer ms;
    ServerSocket serverSocket;

    NewLangListener(MainServer ms){
        this.ms = ms;
        try {
            serverSocket = new ServerSocket(1001, 0, InetAddress.getByName("localhost"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.start();
    }

    public void run(){
        while(true){
            try {
                Socket client = serverSocket.accept();
                new NewLangHandler(client,ms).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
