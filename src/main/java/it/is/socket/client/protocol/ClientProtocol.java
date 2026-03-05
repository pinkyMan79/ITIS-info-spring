package it.is.test.client.protocol;

import java.io.Serializable;
import java.util.UUID;

public class ClientProtocol implements Serializable {

    public UUID clientId;
    public char sym;
    public int x;
    public int y;

    public ClientProtocol(UUID clientId, char sym, int x, int y) {
        this.clientId = clientId;
        this.sym = sym;
        this.x = x;
        this.y = y;
    }
}
