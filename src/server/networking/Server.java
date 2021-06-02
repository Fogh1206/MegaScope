package server.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private boolean running = true;
    private Socket socketClient;

    /**
     * Void method for starting the server and accepting the clients.
     * @throws IOException
     */
    public void startServer() throws IOException {
        serverSocket = new ServerSocket(2910);
        System.out.println("Server started..");
        while (running) {
            try {
                System.out.println("Waiting for client..");
                socketClient = serverSocket.accept();
                ServerSocketHandler ssh = new ServerSocketHandler(socketClient);
                (new Thread(ssh)).start();
                System.out.println("Client connected");
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }
}



