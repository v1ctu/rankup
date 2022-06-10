package com.github.v1ctu.command;

import com.github.v1ctu.RankupPlugin;
import com.github.v1ctu.entity.Rank;
import com.github.v1ctu.entity.UserEntity;
import lombok.AllArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class RankupCommand {

    private final RankupPlugin rankupPlugin;

    @Command(name = "rankup", target = CommandTarget.PLAYER)
    public void handleCommand(Context<Player> context) {
        Player player = context.getSender();
        UserEntity userEntity = rankupPlugin.getUserEntityCache().get(player.getUniqueId());
        if (userEntity == null || !userEntity.canRankup()) {
            player.sendMessage("§cVoce nao pode upar de rank!");
            return;
        }

        Rank currentRank = userEntity.getRank();
        Rank nextRank = rankupPlugin.getRankCache().get(rank -> rank.getPosition() == (currentRank.getPosition() + 1));

        rankupPlugin.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), nextRank.getPrice());
        userEntity.setRank(nextRank);

        player.sendMessage("§aVocê evoluiu para o rank " + nextRank.getName() + "§a.");
    }

}
