package it.is.test.client.protocol;

import java.io.Serializable;
import java.util.UUID;

public class LoginProtocol implements Serializable {

    public UUID clientId;
    public char sym;

    public LoginProtocol(UUID uuid, char sym) {
        this.clientId = uuid;
        this.sym = sym;
    }
}
