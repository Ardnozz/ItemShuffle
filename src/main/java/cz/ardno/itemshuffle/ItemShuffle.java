package cz.ardno.itemshuffle;

import cz.ardno.itemshuffle.commands.ItemShuffleCommand;
import cz.ardno.itemshuffle.commands.ItemShuffleTabCompleter;
import cz.ardno.itemshuffle.listeners.PlayerInventoryClickListener;
import cz.ardno.itemshuffle.listeners.PlayerJoinListener;
import cz.ardno.itemshuffle.listeners.PlayerLeaveListener;
import cz.ardno.itemshuffle.listeners.PlayerPickUpListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemShuffle extends JavaPlugin {

    private static ItemShuffle instance;

    @Override
    public void onEnable() {
        instance = this;

        this.getCommand("itemshuffle").setExecutor(new ItemShuffleCommand());
        this.getCommand("itemshuffle").setTabCompleter(new ItemShuffleTabCompleter());

        this.getServer().getPluginManager().registerEvents(new PlayerInventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerPickUpListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ItemShuffle getInstance() {
        return instance;
    }
}
