package it.is.test.client.processor;

import it.is.test.client.GlobalClientStreamContainer;
import it.is.test.client.processor.lock.MonitorContainer;
import it.is.test.server.protocol.ServerBroadcastProtocol;
import it.is.test.server.protocol.dto.PlayerInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerStreamProcessor {
    private final char[][] playground;
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    public boolean isRunning = true;

    public ServerStreamProcessor(GlobalClientStreamContainer globalClientStreamContainer, char[][] playground) throws IOException {
        this.socket = globalClientStreamContainer.getClientSocket();
        this.objectInputStream = globalClientStreamContainer.getObjectInputStream();
        this.playground = playground;
    }

    public void startProcess() {
        Thread serverCallbackListener = new Thread(this::listen, "serverCallbackListener");
        serverCallbackListener.setDaemon(true);
        serverCallbackListener.start();
    }

    public void listen() {
        try {
            while (!socket.isClosed() && isRunning) {
                handleServerEvent();
            }
        } catch (Exception e) {
            if (isRunning) {
                System.err.println("Connection lost: " + e.getMessage());
            }
        } finally {
            close();
        }
    }

    private void handleServerEvent() {
        try {
            ServerBroadcastProtocol serverBroadcastProtocol = (ServerBroadcastProtocol) objectInputStream.readObject();
            synchronized (MonitorContainer.playgroundMonitor) {
                for (PlayerInfo playerInfo : serverBroadcastProtocol.playerInfoSet) {
                    playground[playerInfo.y][playerInfo.x] = playerInfo.sym;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Something went wrong on sever event handling");
        }
    }

    private void close() {
        isRunning = false;
        try {
            objectInputStream.close();
            if (!socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
    }
}
