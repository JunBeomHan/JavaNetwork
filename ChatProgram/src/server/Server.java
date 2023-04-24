package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int clientsCnt = -1;
    private static Socket[] clients = new Socket[100];

    public static void main(String[] args) {
        startTCPServer(9190);
    }

    private static void startTCPServer(int port) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("[TCP Server] starting");
                    ServerSocket serverSocket = new ServerSocket(port);

                    while(true) {
                        Socket socket = serverSocket.accept();
                        clients[++clientsCnt] = socket;
                        System.out.println("[TCP Server] New Connection " + ((InetSocketAddress) socket.getRemoteSocketAddress()).getHostString());
                        clientHandler(socket);
                    }


                } catch(Exception e) {
                    System.out.println("[TCP Server] " + e.getMessage());
                }
            }
        };
        thread.start();
    }

    private static void clientHandler(Socket socket) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // TODO: 그 뭐냐 상대방 연결 끊기면 소켓 배열 끌어올리는거 구현해야 함.
                    while(true) {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        String receiveMessage = dataInputStream.readUTF();
                        if(receiveMessage == null) {
                            System.out.println("NULL!");
                            break;
                        }
                        transmitAll(receiveMessage);
                    }

                    int socketIndex = -1;
                    for(Socket client : clients) {
                        socketIndex++;
                        if(client == socket) break;
                    }

                    for(int i = socketIndex; i < clients.length; i++) {
                        clients[i] = clients[i + 1];
                    }

                    socket.close();

                } catch (Exception e) {
                    System.out.println("[TCP Server " +
                            ((InetSocketAddress) socket.getRemoteSocketAddress()).getHostString() + "]" +
                                e.getMessage());
                }
            }
        };
        thread.start();
    }

    private static void transmitAll(String receiveMessage) {
        try {
            for (Socket client : clients) {
                 if(client == null) continue;
                DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                dataOutputStream.writeUTF(receiveMessage);
                dataOutputStream.flush();
            }
        } catch (Exception e) {
            System.out.println("transmitAll[TCP Server] " + e.getMessage());
        }
    }

}


