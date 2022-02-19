package cz.ardno.itemshuffle.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Thanks for downloading ItemShuffle\n" +
                "It was created by Ardno\n" +
                "https://www.youtube.com/channel/UCYWLZwUZUCWAEfMcqkJ1-9w");
    }
}
