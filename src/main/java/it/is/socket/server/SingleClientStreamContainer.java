package it.is.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SingleClientStreamContainer {

    public final Object clientLock = new Object();
    private final Socket clientSocket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public SingleClientStreamContainer(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void close() throws IOException {
        objectInputStream.close();
        objectOutputStream.close();
        clientSocket.close();
    }
}
