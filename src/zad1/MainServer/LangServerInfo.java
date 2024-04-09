package zad1.MainServer;

import java.net.InetAddress;

public class LangServerInfo {
    private String lang;
    private InetAddress adress;
    private int port;

    public LangServerInfo(String lang, InetAddress adress, int port) {
        this.lang = lang;
        this.adress = adress;
        this.port = port;
    }

    public String getLang() {
        return lang;
    }

    public InetAddress getAdress() {
        return adress;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "LangServerInfo{" +
                "lang='" + lang + '\'' +
                ", adress=" + adress +
                ", port=" + port +
                '}';
    }
}
