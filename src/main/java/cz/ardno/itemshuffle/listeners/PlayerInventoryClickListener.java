package cz.ardno.itemshuffle.listeners;

import cz.ardno.itemshuffle.utilities.ItemShuffleTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickListener implements Listener {

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        if (ItemShuffleTimer.exists("id")) {
            if (ItemShuffleTimer.containsActive(((Player) event.getWhoClicked()))) {
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().getType().equals(ItemShuffleTimer.getPlayerMaterial((Player) event.getWhoClicked()))) {
                        ItemShuffleTimer.addPlayerComplete((Player) event.getWhoClicked());
                        if (ItemShuffleTimer.hasEverybodyComplete()) {
                            ItemShuffleTimer.getActive().forEach(p -> p.sendMessage("§aEverybody has completed their items!"));
                            ItemShuffleTimer.getTimer("id").interrupt();
                            return;
                        }
                        ItemShuffleTimer.getActive().forEach(p -> p.sendMessage("§a" + event.getWhoClicked().getName() + " has completed their item!"));
                    }
                }
            }
        }
    }
}
