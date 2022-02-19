package cz.ardno.itemshuffle.utilities;

import cz.ardno.itemshuffle.enumerators.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ItemShuffleTimer {

    private Timer t;
    private String id;

    private static final Map<String, ItemShuffleTimer> instance = new HashMap<>();
    private static final Map<Player, Material> item = new HashMap<>();

    private static final List<Player> active = new ArrayList<>();
    private static final List<Player> completed = new ArrayList<>();

    public static boolean bothMode = false;

    public void init(String id) {
        init(id, 300);
    }

    public void init(String id, int time) {
        this.t = new Timer();
        this.id = id;
        PhaseHandler.increase();

        instance.put(id, this);

        Random random = ThreadLocalRandom.current();

        int i;
        List<Material> materials;
        if (PhaseHandler.getPhase() <= 4) materials = AvailableItemsP1.getItems();
        else if (PhaseHandler.getPhase() <= 9) {
            i = random.nextInt(2);
            if (i == 0) materials = AvailableItemsP1.getItems();
            else materials = AvailableItemsP2.getItems();
        } else if (PhaseHandler.getPhase() <= 13) {
            i = random.nextInt(3);
            if (i == 0) materials = AvailableItemsP1.getItems();
            else if (i == 1) materials = AvailableItemsP2.getItems();
            else materials = AvailableItemsP3.getItems();
        } else if (PhaseHandler.getPhase() <= 17) {
            i = random.nextInt(4);
            if (i == 0) materials = AvailableItemsP1.getItems();
            else if (i == 1) materials = AvailableItemsP2.getItems();
            else if (i == 2) materials = AvailableItemsP3.getItems();
            else materials = AvailableItemsP4.getItems();
        } else if (PhaseHandler.getPhase() <= 23) {
            i = random.nextInt(5);
            if (i == 0) materials = AvailableItemsP1.getItems();
            else if (i == 1) materials = AvailableItemsP2.getItems();
            else if (i == 2) materials = AvailableItemsP3.getItems();
            else if (i == 3) materials = AvailableItemsP4.getItems();
            else materials = AvailableItemsP5.getItems();
        } else {
            i = random.nextInt(6);
            if (i == 0) materials = AvailableItemsP1.getItems();
            else if (i == 1) materials = AvailableItemsP2.getItems();
            else if (i == 2) materials = AvailableItemsP3.getItems();
            else if (i == 3) materials = AvailableItemsP4.getItems();
            else if (i == 4) materials = AvailableItemsP5.getItems();
            else materials = AvailableItemsP6.getItems();
        }

        if (bothMode) {
            Material bothMaterial = materials.get(random.nextInt(materials.size()));
            for (Player p : active) {
                item.put(p, bothMaterial);
                String materialName = getNameFromEnum(item.get(p));
                p.sendMessage("Your material: " + materialName);
            }
        } else {
            for (Player p : active) {
                item.put(p, materials.get(random.nextInt(materials.size())));
                String materialName = getNameFromEnum(item.get(p));
                p.sendMessage("Your material: " + materialName);
            }
        }

        t.schedule(new TimerTask() {

            int count = time;

            @Override
            public void run() {
                if (count <= 10) {
                    if (count == 0) {
                        for (Player p : active) {
                            p.sendMessage("§4Times up!");
                            if (completed.contains(p)) continue;
                            ItemShuffleTimer.active.forEach(px -> px.sendMessage("§4" + p.getName() + " hasn't completed their item."));
                        }
                        interrupt();
                        return;
                    } else if (count == 1) {
                        ItemShuffleTimer.active.forEach(p -> p.sendMessage("§c1 second left!"));
                        --count;
                        return;
                    }
                    ItemShuffleTimer.active.forEach(p -> p.sendMessage("§c" + count + " seconds left!"));
                }
                --count;
            }
        }, 0, 1000);
    }

    public static List<Player> getActive() {
        return active;
    }

    public void interrupt() {
        instance.remove(id);
        completed.clear();
        new ItemShuffleTimer().init("id");
        t.cancel();
    }

    public void powInterrupt() {
        instance.remove(id);
        completed.clear();
        PhaseHandler.decrease();
        t.cancel();
    }

    public static ItemShuffleTimer getTimer(String id) {
        if (exists(id)) {
            return instance.get(id);
        }
        return null;
    }

    public static boolean hasEverybodyComplete() {
        int i = 0;
        for (Player p : active) {
            if (completed.contains(p)) continue;
            ++i;
        }
        return i == 0;
    }

    private static boolean isActiveAndDoesNotContainComplete(Player player) {
        return active.contains(player) && !completed.contains(player);
    }

    public static void addPlayerComplete(Player player) {
        item.remove(player);
        completed.add(player);
    }

    public static Material getPlayerMaterial(Player player) {
        if (isActiveAndDoesNotContainComplete(player)) {
            return item.get(player);
        }
        return null;
    }

    public static boolean containsActive(Player player) {
        return active.contains(player);
    }

    public static boolean exists(String id) {
        return instance.containsKey(id);
    }

    public String getNameFromEnum(Material m) {
        String[] itemNames = m.name().replaceAll("_", " ").split(" ");
        StringBuilder finalString = new StringBuilder();
        for (String s : itemNames) {
            finalString.append(s.charAt(0)).append(s.substring(1).toLowerCase()).append(" ");
        }
        return finalString.toString();
    }
}
