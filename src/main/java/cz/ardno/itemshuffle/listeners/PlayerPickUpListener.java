package cz.ardno.itemshuffle.listeners;

import cz.ardno.itemshuffle.utilities.ItemShuffleTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickUpListener implements Listener {

    @EventHandler
    public void onPlayerPickUp(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            if (ItemShuffleTimer.exists("id")) {
                if (ItemShuffleTimer.containsActive(((Player) event.getEntity()))) {
                    if (event.getItem().getItemStack().getType().equals(ItemShuffleTimer.getPlayerMaterial((Player) event.getEntity()))) {
                        ItemShuffleTimer.addPlayerComplete(((Player) event.getEntity()));
                        if (ItemShuffleTimer.hasEverybodyComplete()) {
                            ItemShuffleTimer.getActive().forEach(p -> p.sendMessage("§aEverybody has completed their items!"));
                            ItemShuffleTimer.getTimer("id").interrupt();
                            return;
                        }
                        ItemShuffleTimer.getActive().forEach(p -> p.sendMessage("§a" + event.getEntity().getName() + " has completed their item!"));
                    }
                }
            }
        }
    }
}
