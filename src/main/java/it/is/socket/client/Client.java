package it.is.test.client;

import it.is.test.client.processor.ClientInputProcessor;
import it.is.test.client.processor.MapRewriterProcessor;
import it.is.test.client.processor.ServerStreamProcessor;
import it.is.test.client.protocol.LoginProtocol;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class Client {

    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final UUID id = UUID.randomUUID();

    private final ClientInputProcessor clientInputProcessor;
    private final MapRewriterProcessor mapRewriterProcessor;
    private final ServerStreamProcessor serverStreamProcessor;

    private char[][] initMap = new char[20][20];

    public Client(GlobalClientStreamContainer globalClientStreamContainer, char sym) throws IOException {
        this.socket = globalClientStreamContainer.getClientSocket();
        this.objectOutputStream = globalClientStreamContainer.getObjectOutputStream();
        initialRender();
        logIn(sym);

        this.clientInputProcessor = new ClientInputProcessor(globalClientStreamContainer, id, sym);
        this.mapRewriterProcessor = new MapRewriterProcessor(initMap);
        this.serverStreamProcessor = new ServerStreamProcessor(globalClientStreamContainer, initMap);
    }

    public void logIn(char sym) {
        try {
            objectOutputStream.writeObject(new LoginProtocol(id, sym));
            objectOutputStream.flush();
            objectOutputStream.reset();
        } catch (IOException e) {
            System.err.println("Error in log in, restart application: " + e.getMessage());
        }
    }

    public void initClientInputProcessor() {
        clientInputProcessor.startInputProcessing();
    }

    public void initMapRewriterProcessor() {
        mapRewriterProcessor.startRewriter();
    }

    public void initServerStreamProcessor() {
        serverStreamProcessor.startProcess();
    }

    private void initialRender() {
        for (int i = 0; i < initMap.length; i++) {
            for (int j = 0; j < initMap[0].length; j++) {
                initMap[i][j] = '*';
            }
        }
    }
}
