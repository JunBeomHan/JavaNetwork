package UDPExample02;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClient {
    public static void main(String[] args) {
        try {
            //DatagramSocket 생성
            DatagramSocket datagramSocket = new DatagramSocket();

            // 구독하고 싶은 뉴스 주제 보내기
            String data = "정치";
            byte[] bytes = data.getBytes("UTF-8");
            DatagramPacket sendPacket = new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress("localhost", 50001));
            datagramSocket.send(sendPacket);

            while (true) {
                // 현재는 정적으로 크기를 할당했지만, 실제로는 가변으로 할당 시켜야 함.
                DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
                datagramSocket.receive(receivePacket);

                String news = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
                System.out.println(news);

                //10 번째 뉴스를 받으면 while문 종료
                if(news.contains("뉴스10")) {
                    System.out.println("[client] 연결 종료");
                    break;
                }
            }

            // DatagramSocket 닫기
            datagramSocket.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
