package it.is.socket.server;

import it.is.socket.server.protocol.dto.PlayerInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerLocationsGlobalContainer {

    private static final Map<UUID, PlayerInfo> playerInfos = new ConcurrentHashMap<>();

    public static void savePlayerInfo(PlayerInfo playerInfo, UUID uuid) {
        PlayerInfo put = playerInfos.put(uuid, playerInfo);
        System.out.println("PlayerInfo saved: " + playerInfo);
        if (put != null) {
            System.out.println("PlayerInfo updated: " + playerInfo);
        }
    }

    public static Map<UUID, PlayerInfo> getPlayerInfos() {
        return new HashMap<>(playerInfos);
    }

    public static Set<PlayerInfo> getPlayerInfoSet() {
        return new HashSet<>(playerInfos.values());
    }

    public static Set<UUID> getPlayerIdSet() {
        return new HashSet<>(playerInfos.keySet());
    }
}
