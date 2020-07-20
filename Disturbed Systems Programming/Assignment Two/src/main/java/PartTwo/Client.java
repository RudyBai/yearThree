package PartTwo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private Scanner keyboard;

    public Client() {
        this.keyboard = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void connect() throws InterruptedException {
        try {
            this.socket = new Socket("localhost", 7999);
        } catch (IOException e) {
            if (this.socket == null) {
                Thread.sleep(500);
                System.out.println("<CLIENT> Retrying connection");
                this.connect();
            } else {
                e.printStackTrace();
            }
        }
    }

    private void sendRequest(String request) {
        if (request.startsWith("Bob")) {
            try {
                toServer.writeUTF(request);
                toServer.flush();
                String response = fromServer.readUTF();
                System.out.println("<CLIENT> " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            this.connect();
            this.fromServer = new DataInputStream(socket.getInputStream());
            this.toServer = new DataOutputStream(socket.getOutputStream());
            System.out.println("<CLIENT> Connected to server at localhost:7999.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.print("<CLIENT> Please enter a request : ");
            this.sendRequest(keyboard.nextLine());

        }
    }

}
