package cz.ardno.itemshuffle.listeners;

import cz.ardno.itemshuffle.utilities.ItemShuffleTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (ItemShuffleTimer.exists("id") && ItemShuffleTimer.containsActive((event.getPlayer()))) {
            ItemShuffleTimer.getActive().remove(event.getPlayer());
        }
    }
}
