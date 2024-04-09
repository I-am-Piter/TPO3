package zad1.MainServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class NewLangHandler extends Thread{
    Socket connection;
    BufferedReader in;
    MainServer ms;
    InetAddress langAdress;

    public NewLangHandler(Socket connection, MainServer ms){
        this.connection = connection;
        this.ms = ms;
    }

    public void run() {
        try {
            String input = "";
            String tmpInput = "";
            this.in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((tmpInput = in.readLine()) != null){
                input += tmpInput;
            }
            String[] data = input.split(",");
            ms.addLangServer(data[0],connection.getInetAddress(),Integer.parseInt(data[1]));
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
