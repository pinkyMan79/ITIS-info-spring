package it.is.socket.server;

import it.is.socket.CommonVars;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerInit {

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                20,
                1000L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        Server server = new Server(new ServerSocket(CommonVars.SERVER_PORT), threadPoolExecutor);
        server.initBroadcastRenderProcessor();
        System.out.println("Server started");

        while (true) {
            try {
                SingleClientStreamContainer socketData = server.acceptNewConnection();
                server.initClientInputProcessor(socketData);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
