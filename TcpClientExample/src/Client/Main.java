package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Thread{
    private final static String HOSTNAME = "localhost";
    private final static int PORT = 9109;



    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket(HOSTNAME, PORT);
            System.out.println("Server connected");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            String inputData = "";

            while(!inputData.equals("exit")) {
                System.out.println("[TEXT]:");
                printWriter.println(scanner.next());
                printWriter.flush();

                System.out.println("from Server" + bufferedReader.readLine());
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

    }



}