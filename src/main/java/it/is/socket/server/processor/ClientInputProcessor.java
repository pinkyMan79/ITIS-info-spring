package it.is.socket.server.processor;

import it.is.socket.client.protocol.ClientProtocol;
import it.is.socket.server.PlayerLocationsGlobalContainer;
import it.is.socket.server.SingleClientStreamContainer;
import it.is.socket.server.protocol.dto.PlayerInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.UUID;

public class ClientInputProcessor {

    private final Map<UUID, SingleClientStreamContainer> clients;

    public ClientInputProcessor(Map<UUID, SingleClientStreamContainer> clients) {
        this.clients = clients;
    }

    public void handleClient(SingleClientStreamContainer socketContainer) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = socketContainer.getObjectInputStream();
        while (!socketContainer.getClientSocket().isClosed()) {
            ClientProtocol clientProtocol = (ClientProtocol) objectInputStream.readObject();
            System.out.println("Accept new data from client: " + clientProtocol.clientId);
            PlayerLocationsGlobalContainer.savePlayerInfo(new PlayerInfo(clientProtocol.x, clientProtocol.y, clientProtocol.sym), clientProtocol.clientId);
        }
    }

}
