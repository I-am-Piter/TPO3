package zad1.LangServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LangServer extends Thread{
    ServerSocket ss;
    Dict slownik;

    public LangServer(ServerSocket ss, Dict slownik) {
        this.ss = ss;
        this.slownik = slownik;

        try {
            Socket giveInfoToMainServer = new Socket("localhost",1001);
            PrintWriter outWriter = new PrintWriter(giveInfoToMainServer.getOutputStream());
            outWriter.println(slownik.getCode()+","+ss.getLocalPort());
            outWriter.flush();
            outWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.start();
    }

    public void run(){
        while(true){
            try {
                Socket client = ss.accept();
                System.out.println("client accepted");
                new LangHandler(client,slownik).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Map<String,String> EN = new HashMap<>();
        EN.put("polska","poland");
        EN.put("dom","house");
        EN.put("informatyka","computer science");

        Dict ENDict = new Dict("EN",EN);

        Map<String,String> FR = new HashMap<>();
        FR.put("polska","pologne");
        FR.put("dom","maison");
        FR.put("informatyka","informatique");

        Dict FRDict = new Dict("FR",FR);

        Map<String,String> JP = new HashMap<>();
        JP.put("polska","ポーランド");
        JP.put("dom","家");
        JP.put("informatyka","情報技術");

        Dict JPDict = new Dict("JP",JP);

        try {
            int port = 2000;
            new LangServer(new ServerSocket(port++),ENDict);
            new LangServer(new ServerSocket(port++),FRDict);
            new LangServer(new ServerSocket(port++),JPDict);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(Arrays.toString(e.getStackTrace()));
        }

    }
}
