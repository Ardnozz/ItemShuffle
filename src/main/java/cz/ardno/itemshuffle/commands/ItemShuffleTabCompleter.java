package cz.ardno.itemshuffle.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ItemShuffleTabCompleter implements TabCompleter {

    private final List<String> arguments = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("start");
            arguments.add("stop");
            arguments.add("resetphase");
            arguments.add("setphase");
            arguments.add("bothmode");
            arguments.add("nextitem");
        }

        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            for (String s : arguments) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) result.add(s);
            }
            return result;
        }
        return null;
    }
}
