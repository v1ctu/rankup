package com.github.v1ctu.entity;

import com.github.v1ctu.RankupPlugin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.milkbowl.vault.economy.Economy;
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

    private static final RankupPlugin rankupPlugin = RankupPlugin.getInstance();

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

    public boolean canRankup() {
        Rank nextRank = rankupPlugin.getRankCache().get(cachedRank -> cachedRank.getPosition() == (rank.getPosition() + 1));
        if (nextRank == null) return false;

        return (rankupPlugin.getEconomy().getBalance(getPlayer()) < nextRank.getPrice());
    }
}
