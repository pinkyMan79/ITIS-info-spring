package it.is.test.server.processor;

import it.is.test.server.PlayerLocationsGlobalContainer;
import it.is.test.server.SingleClientStreamContainer;
import it.is.test.server.protocol.ServerBroadcastProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.UUID;

public class BroadcastRenderProcessor {

    private final Map<UUID,SingleClientStreamContainer> clientStreamContainers;
    private final ServerSocket serverSocket;

    public BroadcastRenderProcessor(Map<UUID, SingleClientStreamContainer> clientStreamContainers, ServerSocket serverSocket) {
        this.clientStreamContainers = clientStreamContainers;
        this.serverSocket = serverSocket;
    }

    public void processBroadcastRendering() {
        while (!serverSocket.isClosed()) {
            clientStreamContainers.forEach((k, v) -> {
                Thread.ofVirtual().start(() -> {
                    try {
                        synchronized (v.clientLock) {
                            if (!v.getClientSocket().isClosed()) {
                                v.getObjectOutputStream().writeObject(new ServerBroadcastProtocol(PlayerLocationsGlobalContainer.getPlayerInfoSet()));
                                v.getObjectOutputStream().flush();
                                v.getObjectOutputStream().reset();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
