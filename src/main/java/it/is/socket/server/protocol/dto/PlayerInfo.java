package it.is.socket.server.protocol.dto;

import java.io.Serializable;

public class PlayerInfo implements Serializable {

    public PlayerInfo(int x, int y, char sym) {
        this.x = x;
        this.y = y;
        this.sym = sym;
    }

    public int x;
    public int y;
    public char sym;
}
