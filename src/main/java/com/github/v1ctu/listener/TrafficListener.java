package com.github.v1ctu.listener;

import com.github.v1ctu.RankupPlugin;
import com.github.v1ctu.database.MongoDB;
import com.github.v1ctu.entity.Rank;
import com.github.v1ctu.entity.UserEntity;
import com.github.v1ctu.util.Task;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@AllArgsConstructor
public class TrafficListener implements Listener {

    private final RankupPlugin rankupPlugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uniqueId = event.getPlayer().getUniqueId();
        if (rankupPlugin.getUserEntityCache().containsKey(uniqueId)) return;

        // Rank rank = rankupPlugin.getRankCache().getValues().stream().toList().get(0);
        Rank firstRank = rankupPlugin.getRankCache().getValues().stream()
            .filter(rank -> rank.getPosition() == 0)
            .findFirst()
            .orElse(null);
        if (firstRank == null) return;

        UserEntity userEntity = UserEntity.builder()
            .uuid(uniqueId)
            .rank(firstRank)
            .build();

        rankupPlugin.getUserEntityCache().put(userEntity.getUuid(), userEntity);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Task.runAsync(() -> {
            UserEntity userEntity = rankupPlugin.getUserEntityCache().get(event.getPlayer().getUniqueId());
            rankupPlugin.getUserEntityDao().replace(userEntity);
        });
    }

}
