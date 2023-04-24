package Exam02;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClientExample {
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 50001);
            System.out.println("[Client] 연결 성공");


            // 데이터 보내기
            String sendMessage = "나는 자바가 좋아";
            DataOutputStream dataOUtputStream = new DataOutputStream(socket.getOutputStream());
            dataOUtputStream.writeUTF(sendMessage);
            dataOUtputStream.flush();
            System.out.println("[Client] 데이터를 보냅니다." + sendMessage);


            // 데이터 받기
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String receiveMessage = dataInputStream.readUTF();
            System.out.println("[Client] 데이터를 받았습니다. " + receiveMessage);

            socket.close();
            System.out.println("[Client] 연결 끊음 ");

        } catch (UnknownHostException e) {
            // IP 또는 도메인 표기 방법이 잘못되었을 경우
            System.out.println("UnknownHostException: " + e.toString());
        } catch (IOException e) {
            // IP 또는 PORT 번호가 존재하지 않을 경우
            System.out.println("IOException: " + e.toString());
        }
    }

}
