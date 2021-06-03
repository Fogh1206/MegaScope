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
        while (running) {
            try {
                socketClient = serverSocket.accept();
                ServerSocketHandler ssh = new ServerSocketHandler(socketClient);
                (new Thread(ssh)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



