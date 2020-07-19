package me.newtondev.entityexample;

import me.newtondev.entity.FakeEntity;
import me.newtondev.entity.FakeEntityFactory;
import me.newtondev.entity.FakeEntityType;
import me.newtondev.entity.equipment.ItemSlot;
import me.newtondev.entity.event.FakeEntityInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Example extends JavaPlugin implements Listener {

    /*
    * This may be registered to allow the entity custom events.
     */
    public void onEnable() {
        FakeEntityFactory.INSTANCE.register(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    /*
     * Spawning a simple entity for a player.
     */
    public void spawnEntity(Player player) {
        FakeEntity entity = new FakeEntity(FakeEntityType.ZOMBIE)
                .addViewer(player)
                .setLocation(player.getLocation())
                .spawn();

        // All entity attributes must be added after spawn
        entity.addEquipment(ItemSlot.CHEST, new ItemStack(Material.DIAMOND_CHESTPLATE));
        entity.getViewers().forEach(p -> p.sendMessage("An entity has been spawned for you!"));

        // Make the entity look at player
        Bukkit.getScheduler().runTaskTimer(this, () -> entity.lookAt(player.getLocation()), 1L, 1L);
    }

    @EventHandler
    public void onFakeEntityClick(FakeEntityInteractEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.GREEN + "You just clicked a fake entity.");
    }
}
