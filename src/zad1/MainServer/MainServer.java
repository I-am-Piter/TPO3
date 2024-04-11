package zad1.MainServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MainServer{
    private Map<String, LangServerInfo> mapa = new HashMap();
    private ServerSocket serverSocket = null;

    public MainServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        new NewLangListener(this);
        listen();
    }
    public LangServerInfo getLangServerInfo(String code){
        return mapa.get(code);
    }

    private void listen(){
        while(true){
            try {
                Socket client = serverSocket.accept();
                new MainHandler(client,this).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addLangServer(String lang, InetAddress adress, int port){
        LangServerInfo tmp = new LangServerInfo(lang,adress,port);
        mapa.put(lang,tmp);
        System.out.println(tmp);
    }

    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(1000);
            new MainServer(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
