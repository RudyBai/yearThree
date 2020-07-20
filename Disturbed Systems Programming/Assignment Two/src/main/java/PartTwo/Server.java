package PartTwo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private static int clientNumber = 0;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    @Override
    public void run() {

        HandleSession handleSession;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7999);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                System.out.println("<SERVER> Waiting for client...");
                Socket client = null;
                if (serverSocket != null) {
                    client = serverSocket.accept();
                }
                System.out.println(String.format("<SERVER> Client %d connected.", clientNumber));
                handleSession = new HandleSession(client, clientNumber);
                clientNumber++;

                handleSession.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}