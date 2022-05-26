package com.github.v1ctu.cache.impl;

import com.github.v1ctu.RankupPlugin;
import com.github.v1ctu.cache.Cache;
import com.github.v1ctu.entity.Rank;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;

@AllArgsConstructor
public class RankCache extends Cache<String, Rank> {

    private final RankupPlugin rankupPlugin;

    public void load() {
        ConfigurationSection ranksSection = rankupPlugin.getConfig().getConfigurationSection("ranks");
        if (ranksSection == null) return;

        for (String id : ranksSection.getKeys(false)) {
            ConfigurationSection rankSection = ranksSection.getConfigurationSection(id);
            if (rankSection == null) continue;

            String name = rankSection.getString("name");
            if (name == null) continue;

            int position = rankSection.getInt("position");
            double price = rankSection.getDouble("price");

            Rank rank = Rank.builder()
                .id(id)
                .name(name.replace("&", "§"))
                .position(position)
                .price(price)
                .build();

            put(rank.getId(), rank);
        }
    }

}
