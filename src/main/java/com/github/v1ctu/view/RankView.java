package com.github.v1ctu.view;

import com.github.v1ctu.RankupPlugin;
import com.github.v1ctu.cache.impl.RankCache;
import com.github.v1ctu.entity.Rank;
import com.github.v1ctu.util.ItemStackBuilder;
import dev.dbassett.skullcreator.SkullCreator;
import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewItem;

import java.util.Comparator;

public class RankView extends PaginatedView<Rank> {

    public RankView(RankCache rankCache){
        super(5, "Ranks");
        setSource(rankCache.getValues()
            .stream().sorted(Comparator.comparingInt(Rank::getPosition))
            .toList());
        setLayout(
            "XXXXXXXXX",
            "XOOOOOOOX",
            "XOOOOOOOX",
            "<OOOOOOO>",
            "XXXXXXXXX"
        );

        setCancelOnClick(true);

    }

    @Override
    protected void onItemRender(PaginatedViewSlotContext<Rank> render, ViewItem item, Rank value) {
        item.withItem(new ItemStackBuilder(SkullCreator.itemFromUrl(value.getUrl()))
                .name(value.getName())
                .lore("§aPreco: " + value.getPrice())
            .build()
        );
    }
}
