package it.is.socket.server.protocol;

import it.is.socket.server.protocol.dto.PlayerInfo;

import java.io.Serializable;
import java.util.Set;

public class ServerBroadcastProtocol implements Serializable {

    public Set<PlayerInfo> playerInfoSet;

    public ServerBroadcastProtocol(Set<PlayerInfo> players) {
        this.playerInfoSet = players;
    }
}
