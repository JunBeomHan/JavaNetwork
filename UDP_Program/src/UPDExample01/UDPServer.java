package UPDExample01;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Scanner;

public class UDPServer {
    private static DatagramSocket datagramSocket;

    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        System.out.println("서버를 종료하려면 q 도는 Q를 입력하고 Enter키를 입력하세요.");
        System.out.println("------------------------------------------------");

        //TCP 서버 시작
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
                    //DatagramSocket 생성 및 port 바인딩
                    datagramSocket = new DatagramSocket(50001);
                    System.out.println("[Server] 시작됨");

                    while(true) {


                        /*
                          DatagramSocket datagramSocket = new DatagramSocket(50001);
                          int bufferSize = datagramSocket.getReceiveBufferSize();
                          DatagramPacket receivePacket = new DatagramPacket(new byte[bufferSize], bufferSize);
                        */

                        // 클라이언트가 구독하고 싶은 뉴스 주제 알기
                        // DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

                        int bufferSize = datagramSocket.getReceiveBufferSize();
                        DatagramPacket receivePacket = new DatagramPacket(new byte[bufferSize], bufferSize);


                        System.out.println("클라이언트의 희망 뉴스 종류를 얻기 위해 대기중");
                        datagramSocket.receive(receivePacket);  // 데이터 받기 전까지 대기 상태 돌입
                        String newsKind = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

                        // 클라이언트의 IP와 PORT 정보가 있는 SocketAddress 얻기
                        SocketAddress socketAddress = receivePacket.getSocketAddress();

                        for(int i = 0; i <= 10; i++) {
                            String data = newsKind + ":뉴스" + i;
                            byte[] bytes = data.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(bytes, 0, bytes.length, socketAddress);
                            datagramSocket.send(sendPacket);

                            Thread.sleep(1000);
                        }
                    }
                } catch(Exception e) {
                    System.out.println("[Server]" + e.getMessage());
                }
            }
        };
        thread.start();
    }

    private static void stopServer() {
        datagramSocket.close();
        System.out.println("[Server] Stopped");
    }

}
