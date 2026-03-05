package it.is.socket.server;

import it.is.socket.client.protocol.LoginProtocol;
import it.is.socket.server.processor.BroadcastRenderProcessor;
import it.is.socket.server.processor.ClientInputProcessor;
import it.is.socket.server.protocol.dto.PlayerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private final ServerSocket serverSocket;
    private final ThreadPoolExecutor clientPool;
    private final Map<UUID, SingleClientStreamContainer> clients = new ConcurrentHashMap<>();
    private final ClientInputProcessor clientInputProcessor;
    private final BroadcastRenderProcessor broadcastRenderProcessor;

    public Server(
            ServerSocket serverSocket,
            ThreadPoolExecutor clientPool
    ) {
        this.serverSocket = serverSocket;
        this.clientPool = clientPool;
        this.clientInputProcessor = new ClientInputProcessor(clients);
        broadcastRenderProcessor = new BroadcastRenderProcessor(clients, serverSocket);
    }

    public SingleClientStreamContainer acceptNewConnection() throws IOException, ClassNotFoundException {
        Socket client = serverSocket.accept();
        System.out.println("New client connected");
        SingleClientStreamContainer singleClientStreamContainer = new SingleClientStreamContainer(client);

        LoginProtocol loginProtocol = (LoginProtocol) singleClientStreamContainer.getObjectInputStream().readObject();

        Optional.ofNullable((loginProtocol).clientId)
                .ifPresentOrElse(
                        definedUUID -> {
                            System.out.println("New client uuid is: " + definedUUID);
                            clients.put(definedUUID, singleClientStreamContainer);
                            PlayerLocationsGlobalContainer.savePlayerInfo(new PlayerInfo(
                                    (int) (1 + Math.random() * 19),
                                    (int) (1 + Math.random() * 19),
                                    loginProtocol.sym
                            ), definedUUID);
                            },
                        () -> {
                            try {
                                singleClientStreamContainer.close();
                            } catch (Exception e) {
                                System.err.println("Error closing connection: " + e.getMessage());
                            }
                        }
        );
        return singleClientStreamContainer;
    }

    public void initClientInputProcessor(SingleClientStreamContainer singleClientStreamContainer) {
        clientPool.submit(() -> {
            try {
                clientInputProcessor.handleClient(singleClientStreamContainer);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void initBroadcastRenderProcessor() {
        Thread broadcastRenderThread = new Thread(() -> {
            broadcastRenderProcessor.processBroadcastRendering();
            System.out.println("Broadcast render process initialized");
        }, "broadcastRenderer");
        broadcastRenderThread.setDaemon(true);
        broadcastRenderThread.start();
    }

    public Set<SingleClientStreamContainer> getClients() {
        return new HashSet<>(clients.values());
    }

    public Map<UUID, SingleClientStreamContainer> getClientsMap() {
        return new HashMap<>(clients);
    }
}
