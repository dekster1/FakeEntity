package me.newtondev.entityexample;

import me.newtondev.entity.FakeEntity;
import me.newtondev.entity.FakeEntityFactory;
import me.newtondev.entity.FakeEntityType;
import me.newtondev.entity.equipment.ItemSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Example extends JavaPlugin {

    /*
    * This may be registered to allow the entity custom events.
     */
    public void onEnable() {
        FakeEntityFactory.INSTANCE.register(this);
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
    }
}
