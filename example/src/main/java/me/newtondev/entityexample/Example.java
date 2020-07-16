package me.newtondev.entityexample;

import me.newtondev.entity.FakeEntity;
import me.newtondev.entity.FakeEntityType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Example extends JavaPlugin {

    public void onEnable() {
        this.getLogger().info("Plugin enabled.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equals("entitytest")) {
                Player p = (Player) sender;
                spawnEntity(p);
            }
        }
        return false;
    }

    public void spawnEntity(Player player) {
        FakeEntity entity = new FakeEntity(FakeEntityType.COW)
                .addViewer(player)
                .setLocation(player.getLocation());

        entity.spawn();
        entity.getViewers().forEach(p -> p.sendMessage("An entity has been spawned for you!"));
    }
}
