package it.is.test.client;

import it.is.test.CommonVars;

import java.io.IOException;
import java.net.Socket;

public class ClientInit {

    public static void main(String[] args) throws IOException, InterruptedException {
        GlobalClientStreamContainer globalClientStreamContainer =
                new GlobalClientStreamContainer(new Socket(CommonVars.HOST, CommonVars.SERVER_PORT));
        Client client = new Client(globalClientStreamContainer, 'R');
        client.initServerStreamProcessor();
        client.initClientInputProcessor();
        client.initMapRewriterProcessor();

        Thread.currentThread().join();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing connection...");
            globalClientStreamContainer.close();
        }));
    }

}
