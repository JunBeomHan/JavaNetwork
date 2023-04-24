package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static final int port = 9190;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", port);
            transmit(socket); receive(socket);
        } catch(UnknownHostException e) {
            // IP 또는 도메인 표기 방법이 잘못되었을 경우
            System.out.println("UnknownHostException: " + e.toString());
        } catch(IOException e) {
            // IP 또는 PORT 번호가 존재하지 않을 경우
            System.out.println("IOException: " + e.toString());
        }
    }

    private static void transmit(Socket socket) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                        String msg;
                        msg = bufferedReader.readLine();

                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF(msg);
                        dataOutputStream.flush();

                    } catch(Exception e) {
                        System.out.println("Exception: " + e.toString());
                    }
                }
            }
        };
        thread.start();
    }

    private static void receive(Socket socket) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        String receiveMessage = dataInputStream.readUTF();

                        System.out.println("[Chat!] : " + receiveMessage);
                    }

                } catch(UnknownHostException e) {
                    // IP 또는 도메인 표기 방법이 잘못되었을 경우
                    System.out.println("UnknownHostException: " + e.toString());
                } catch(IOException e) {
                    // IP 또는 PORT 번호가 존재하지 않을 경우
                    System.out.println("IOException: " + e.toString());
                }

            }
        };
        thread.start();
    }
}
