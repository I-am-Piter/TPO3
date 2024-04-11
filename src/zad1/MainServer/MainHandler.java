package zad1.MainServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MainHandler extends Thread{
    Socket connection;
    BufferedReader in;
    PrintWriter out;
    MainServer ms;
    Socket outSocket;
    InetAddress clientAdress;

    public MainHandler(Socket client, MainServer mainServer) {
        ms = mainServer;
        connection = client;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clientAdress = connection.getLocalAddress();
    }

    public void run() {
        try {
            String input = "";
            String tmpInput = "";
            while((tmpInput = in.readLine()) != null){
                input += tmpInput;
            }
            in.close();
            connection.close();
            String[] data = input.split(",");
            LangServerInfo lsinfo = ms.getLangServerInfo(data[1].toUpperCase().replaceAll(" ",""));
            if(lsinfo == null){
                outSocket = new Socket(connection.getLocalAddress(), Integer.parseInt(data[2]));
                out = new PrintWriter(new OutputStreamWriter(outSocket.getOutputStream()));
                out.println("niepoprawne zapytanie");
            }else {
                outSocket = new Socket(lsinfo.getAdress(), lsinfo.getPort());
                out = new PrintWriter(new OutputStreamWriter(outSocket.getOutputStream()));
                out.println("" + data[0] + "," + clientAdress.toString().replaceAll("/", "") + "," + data[2]);
            }
            out.flush();
            out.close();
            outSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
