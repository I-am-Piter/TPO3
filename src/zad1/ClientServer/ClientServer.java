package zad1.ClientServer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer {
    private int tmpPort;

    ClientServer(){
    }

    public String getTransaltion(String polishWord,String langCode){
        try {
            Socket socket = new Socket("localhost",1000);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            tmpPort = (int)(Math.random()*1000+1);
            output.println(polishWord+","+langCode+","+tmpPort);
            output.flush();
            output.close();
            return getAnswer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAnswer() throws IOException {
        ServerSocket ss = new ServerSocket(tmpPort);
        Socket client = ss.accept();

        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String tmp;
        String data = "";
        while((tmp = input.readLine()) != null){
            data += tmp;
        }

        ss.close();
        client.close();

        return data;
    }

    public static void main(String[] args) {
        ClientServer cs = new ClientServer();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGui(cs);
            }
        });
    }

}
