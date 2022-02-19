package cz.ardno.itemshuffle.commands;

import cz.ardno.itemshuffle.utilities.ItemShuffleTimer;
import cz.ardno.itemshuffle.utilities.PhaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemShuffleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("ardno.itemshuffle")) {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "start":
                        if (!ItemShuffleTimer.exists("id")) {
                            ItemShuffleTimer.getActive().clear();
                            ItemShuffleTimer.getActive().addAll(Bukkit.getOnlinePlayers());
                            new ItemShuffleTimer().init("id");
                            return true;
                        }
                        sender.sendMessage("ItemShuffle is already started!");
                        return true;
                    case "stop":
                        if (ItemShuffleTimer.exists("id")) {
                            ItemShuffleTimer.getTimer("id").powInterrupt();
                            for (Player p : ItemShuffleTimer.getActive()) {
                                p.sendMessage("ItemShuffle has stopped!");
                            }
                            return true;
                        }
                        sender.sendMessage("ItemShuffle isn't started yet!");
                        return true;
                    case "resetphase":
                        if(ItemShuffleTimer.exists("id")) {
                            PhaseHandler.reset();
                            for (Player p : ItemShuffleTimer.getActive()) {
                                p.sendMessage("Phases has been successfully reset!");
                            }
                            ItemShuffleTimer.getTimer("id").interrupt();
                            return true;
                        }
                        sender.sendMessage("ItemShuffle isn't started yet!");
                        return true;
                    case "setphase":
                        if (ItemShuffleTimer.exists("id")) {
                            if(args.length >= 2) {
                                try {
                                    int phase = Integer.parseInt(args[1]);
                                    PhaseHandler.set(phase);
                                    for (Player p : ItemShuffleTimer.getActive()) {
                                        p.sendMessage("Phase has been set to " + phase);
                                    }
                                    ItemShuffleTimer.getTimer("id").interrupt();
                                    return true;
                                } catch (NumberFormatException ignored) {}
                            }
                            sender.sendMessage("Correct usage: /itemshuffle setphase <number>");
                            return true;
                        }
                        sender.sendMessage("ItemShuffle isn't started yet!");
                        return true;
                    case "bothmode":
                        if (ItemShuffleTimer.bothMode) {
                            ItemShuffleTimer.bothMode = false;
                            sender.sendMessage("Both mode has been disabled!");
                        } else {
                            ItemShuffleTimer.bothMode = true;
                            sender.sendMessage("Both mode has been enabled!");
                        }
                        return true;
                    case "nextitem":
                        if (ItemShuffleTimer.exists("id")) {
                            PhaseHandler.decrease();
                            ItemShuffleTimer.getTimer("id").interrupt();
                            return true;
                        }
                        sender.sendMessage("ItemShuffle isn't started yet!");
                        return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }
}
