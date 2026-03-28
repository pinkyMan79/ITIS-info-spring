package it.is.socket.client.processor;

import it.is.socket.client.GlobalClientStreamContainer;
import it.is.socket.client.protocol.ClientProtocol;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ClientInputProcessor {

    private final Socket clientSocket;
    private final ObjectOutputStream objectOutputStream;
    private final Scanner sc = new Scanner(System.in);
    private final UUID id;
    private final char sym;
    public boolean isRunning = true;

    private int currX = 5;
    private int currY = 5;

    public ClientInputProcessor(
            GlobalClientStreamContainer globalClientStreamContainer,
            UUID id,
            char sym
    ) {
        this.clientSocket = globalClientStreamContainer.getClientSocket();
        this.objectOutputStream = globalClientStreamContainer.getObjectOutputStream();
        this.id = id;
        this.sym = sym;
    }

    public void startInputProcessing() {
        Thread thread = new Thread(this::listenInput, "clientInputThread");
        thread.setDaemon(true);
        thread.start();
    }

    private void listenInput() {
        try {
            while (isRunning && !clientSocket.isClosed()) {
                String s = sc.nextLine();
                switch (s) {
                    case "w" -> {
                        currY++;
                        changePosition();
                    }
                    case "a" -> {
                        currX--;
                        changePosition();
                    }
                    case "s" -> {
                        currY--;
                        changePosition();
                    }
                    case "d" -> {
                        currX++;
                        changePosition();
                    }
                    default -> System.out.println("Invalid input");
                }
            }
        } finally {
            close();
        }
    }

    private void changePosition() {
        try {
            objectOutputStream.writeObject(new ClientProtocol(id, sym, currX, currY));
            objectOutputStream.flush();
            objectOutputStream.reset();
        } catch (IOException e) {
            System.err.println("Error in change position, resend needed: " + e.getMessage());
        }
    }

    private void close() {
        isRunning = false;
        try {
            objectOutputStream.close();
            if (!clientSocket.isClosed()) clientSocket.close();
        } catch (Exception ignored) {}
    }
}
