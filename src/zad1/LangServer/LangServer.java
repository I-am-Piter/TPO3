package zad1.LangServer;

import zad1.MainServer.MainHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LangServer {
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
        listen();
    }

    private void listen(){
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

        try {
            ServerSocket ss = new ServerSocket(2000);
            LangServer ls = new LangServer(ss,ENDict);
        } catch (IOException e) {
            throw new RuntimeException(Arrays.toString(e.getStackTrace()));
        }

    }
}
