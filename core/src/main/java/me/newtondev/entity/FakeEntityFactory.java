package me.newtondev.entity;

import io.netty.channel.Channel;
import me.newtondev.entity.packet.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public enum FakeEntityFactory {

    INSTANCE;

    private Plugin plugin;

    public void register(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new FakeEntityListener(), plugin);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public boolean isRegistered() {
        return (plugin != null);
    }

    private Set<FakeEntity> entities = new HashSet<>();

    public void addEntity(FakeEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(FakeEntity entity) {
        entities.remove(entity);
    }

    public Set<FakeEntity> getEntities() {
        return entities;
    }

    private static class FakeEntityListener implements Listener {

        @EventHandler
        public void onQuit(PlayerQuitEvent e) {
            Channel channel = PacketListener.getChannel(e.getPlayer());
            if ((channel != null) && (channel.pipeline().get("fake_entity_interact") != null)) {
                channel.pipeline().remove("fake_entity_interact");
            }
        }
    }
}
