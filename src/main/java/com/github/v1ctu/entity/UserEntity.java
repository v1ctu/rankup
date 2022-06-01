package com.github.v1ctu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserEntity {

    private UUID uuid;
    private Rank rank;

    @BsonIgnore
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @BsonIgnore
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public String getName() {
        Player player = getPlayer();
        OfflinePlayer offlinePlayer = getOfflinePlayer();
        return (player == null) ? offlinePlayer.getName() : player.getName();
    }
}
