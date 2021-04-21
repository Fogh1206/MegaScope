package server;

import server.data.Users;
import server.modelserver.ServerModel;
import server.modelserver.ServerModelManager;
import server.networking.Server;

import java.io.IOException;

public class RunServer {
    public static void main(String[] args) {

        Users users= new Users();
        ServerModel sm = new ServerModelManager(users);
        Server server = new Server(sm);
        try {
           server.startServer();
        } catch (IOException e) {
            System.out.println("Complete failure to launch");
            e.printStackTrace();
        }



    }

    }

