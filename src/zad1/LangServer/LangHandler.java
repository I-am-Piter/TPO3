package zad1.LangServer;

import java.io.*;
import java.net.Socket;

public class LangHandler extends Thread{
    Socket connection;
    BufferedReader in;
    PrintWriter out;
    Dict slownik;
    public LangHandler(Socket client, Dict slownik) {
        connection = client;
        this.slownik = slownik;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
        try {
            System.out.println("langhandler got connection");
            String input = "";
            String tmpInput = "";
            while((tmpInput = in.readLine()) != null){
                input += tmpInput;
            }
            String[] data = input.split(",");
            in.close();
            connection.close();

            String tlumaczenie = slownik.getTranslation(data[0].replaceAll(" ",""));

            int portKlienta = Integer.parseInt(data[2]);
            System.out.println(data[1]);
            Socket client = new Socket(data[1],portKlienta);

            out = new PrintWriter(client.getOutputStream());
            if(tlumaczenie != null) {
                out.println(tlumaczenie);
            }else{
                out.println("brak slowa w bazie");
            }
            out.flush();
            out.close();
            client.close();

            System.out.println("langhandler sent connection");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
