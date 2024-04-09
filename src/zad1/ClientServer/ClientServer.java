package zad1.ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientServer {

    ClientServer(){
    }

    public void getTransaltion(String polishWord){
        try {
            Socket socket = new Socket("localhost",1000);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            output.println(polishWord+",EN,1500");
            output.flush();
            output.close();
            System.out.println(getAnswer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAnswer() throws IOException {
        ServerSocket ss = new ServerSocket(1500);
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
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("wprowadz slowo");
            cs.getTransaltion(scanner.nextLine());
        }
    }

}
