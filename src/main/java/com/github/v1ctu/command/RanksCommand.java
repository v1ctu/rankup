package com.github.v1ctu.command;

import com.github.v1ctu.RankupPlugin;
import com.github.v1ctu.entity.Rank;
import com.github.v1ctu.entity.UserEntity;
import lombok.AllArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RanksCommand {

    private final RankupPlugin rankupPlugin;

    @Command(name = "ranks", target = CommandTarget.PLAYER)
    public void handleCommand(Context<Player> context) {
        Player player = context.getSender();
        player.sendMessage(new String[]{
            "",
            "§e§lRANKS",
            ""
        });

        List<Rank> ranks = rankupPlugin.getRankCache().getValues().stream()
            .sorted(Comparator.comparingInt(Rank::getPosition))
            .toList();
        for (Rank rank : ranks) {
            player.sendMessage(rank.getName() + " §8- §7" + rank.getPrice());
        }

        player.sendMessage("");
    }

}
