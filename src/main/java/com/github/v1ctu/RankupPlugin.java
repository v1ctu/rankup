package com.github.v1ctu;

import com.github.v1ctu.cache.impl.RankCache;
import com.github.v1ctu.cache.impl.UserEntityCache;
import com.github.v1ctu.command.RanksCommand;
import com.github.v1ctu.command.RankupCommand;
import com.github.v1ctu.command.RankupOtherCommand;
import com.github.v1ctu.dao.UserEntityDao;
import com.github.v1ctu.database.MongoDB;
import com.github.v1ctu.entity.UserEntity;
import com.github.v1ctu.listener.TrafficListener;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RankupPlugin extends JavaPlugin {

    private Economy economy;

    private MongoDB mongoDB;

    private UserEntityCache userEntityCache;
    private UserEntityDao userEntityDao;

    private RankCache rankCache;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        mongoDB = new MongoDB("");
        mongoDB.startConnection("", "");

        userEntityDao = new UserEntityDao(mongoDB);

        rankCache = new RankCache(this);
        rankCache.load();

        userEntityCache = new UserEntityCache(this);
        userEntityCache.load();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            userEntityCache.save();
        }, 5 * 60 * 20, 0);

        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerAdapter(UserEntity.class, message -> userEntityCache.get(userEntity -> userEntity.getName().equals(message)));
        bukkitFrame.registerCommands(
            new RankupCommand(this),
            new RanksCommand(this),
            new RankupOtherCommand(this)
        );

        Bukkit.getPluginManager().registerEvents(new TrafficListener(this), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public void onDisable() {
        userEntityCache.save();
        mongoDB.closeConnection();
    }

}
