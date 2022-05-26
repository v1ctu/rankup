package com.github.v1ctu.command;

import com.github.v1ctu.RankupPlugin;
import com.github.v1ctu.entity.Rank;
import com.github.v1ctu.entity.UserEntity;
import lombok.AllArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


@AllArgsConstructor
public class RankupOtherCommand {

    private final RankupPlugin rankupPlugin;

    @Command(name = "rankup.other", target = CommandTarget.PLAYER, usage= "")
    public void handleCommand(Context<Player> context, UserEntity userEntity) {

        Player player = context.getSender();

        UserEntity target = rankupPlugin.getUserEntityCache().get(userEntity.getUuid());
        if (target == null) return;

        Rank currentRank = target.getRank();
        Rank nextRank = rankupPlugin.getRankCache().get(rank -> rank.getPosition() == (currentRank.getPosition() + 1));
        if (nextRank == null) {
            player.sendMessage("Já acabou o rank do filho esquece.");
            return;
        }

        Economy economy = rankupPlugin.getEconomy();
        if (economy.getBalance(player) < nextRank.getPrice()) {
            player.sendMessage("§cVocê não tem dinheiro, pobre.");
            return;
        }

        economy.withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), nextRank.getPrice());
        userEntity.setRank(nextRank);
        player.sendMessage("§aVocê evoluiu o rank do fofo" + userEntity.getName() + "para o rank " + nextRank.getName() + "§a.");

    }

}
