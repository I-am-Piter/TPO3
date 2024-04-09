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
            System.out.println("mainhandler got connection");
            String input = "";
            String tmpInput = "";
            while((tmpInput = in.readLine()) != null){
                input += tmpInput;
            }
            in.close();
            connection.close();
            String[] data = input.split(",");
            LangServerInfo lsinfo = ms.getLangServerInfo(data[1]);
            outSocket = new Socket(lsinfo.getAdress(),lsinfo.getPort());
            out = new PrintWriter(new OutputStreamWriter(outSocket.getOutputStream()));
            out.println(""+data[0]+","+clientAdress.toString().replaceAll("/", "")+","+data[2]);
            out.flush();
            out.close();
            outSocket.close();
            System.out.println("mainhandler sent connection");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
