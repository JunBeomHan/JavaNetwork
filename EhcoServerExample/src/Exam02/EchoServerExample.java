package Exam02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServerExample {
    private static ServerSocket serverSocket;

    public static void main(String[] args){
        System.out.println("-----------------------------------------------");
        System.out.println("서버를 종료하려면 q 또는 Q를 입려가혹 Enter를 입력하세요.");
        System.out.println("-----------------------------------------------");

        // Start tcp server
        startServer();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            String key = scanner.nextLine();

            if(key.toLowerCase().equals("q")) {
                break;
            }
        }

        scanner.close();
        stopServer();
    }

    private static void startServer() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(50001);
                    System.out.println("[Server] 시작 됨");

                    while(true) {
                        System.out.println("\n[Server] 연결 요청을 기다립니다.");

                        // 연결 수락
                        Socket socket = serverSocket.accept();

                        // 연결된 클라이언트 정보 얻기
                        InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

                        String clientIP = inetSocketAddress.getHostString();
                        System.out.println("[Server]" + clientIP + "의 연결을 수락함");

                        // 데이터 받기 (보조 스트림v)
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        String message = dataInputStream.readUTF();
                        System.out.println("[Server] " + clientIP + message);

                        // 데이터 보내기
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF(message);
                        dataOutputStream.flush();
                        System.out.println("[Server]" + clientIP + message);

                        // 연결 끊기
                        socket.close();
                        System.out.println("[Server]" + clientIP + "의 연결을 끊습니다.");
                    }
                } catch(Exception e) {
                    System.out.println("[Server]" + e.getMessage());
                }
            }
        };
        thread.start();
    }
    private static void stopServer() {
        try {
            serverSocket.close();
        } catch(Exception e) {

        }
    }


}