package UDPExample02;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UPDServerExample {
    private static DatagramSocket datagramSocket;
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        startServer();

        Scanner scanner = new Scanner(System.in);

        while(true)
        {
            String key = scanner.nextLine();

            if(key.toLowerCase().equals("q")) {
                break;
            }
        }

        stopServer();
    }

    private static void startServer() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    datagramSocket = new DatagramSocket(50001);
                    System.out.println("[Sever] 시작 됨");

                    while(true)
                    {
                        DatagramPacket receivepacket = new DatagramPacket(new byte[1024], 1024);
                        System.out.println("클라이언트의 희망 뉴스 종류를 얻기 위해 대기중");
                        datagramSocket.receive(receivepacket);

                        executorService.execute(()-> {
                            String newKind = new String(receivepacket.getData(), receivepacket.getLength(), "UTF-8");
                            
                        });
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
    private static void stopServer() {

    }


}