package me.newtondev.entity;

import me.newtondev.entity.packet.netty.ChannelInjector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public enum FakeEntityFactory {

    INSTANCE;

    private Plugin plugin;
    private ChannelInjector channelInjector;

    public void register(Plugin plugin) {
        this.plugin = plugin;
        this.channelInjector = new ChannelInjector(plugin);
        Bukkit.getPluginManager().registerEvents(new FakeEntityListener(), plugin);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public ChannelInjector getChannelInjector() {
        return channelInjector;
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

        private final ChannelInjector injector = FakeEntityFactory.INSTANCE.getChannelInjector();

        @EventHandler
        public void onQuit(PlayerQuitEvent e) {
            Player player = e.getPlayer();
            injector.uninjectPlayer(player);
        }

        @EventHandler
        public void onPluginDisable(PluginDisableEvent e) {
            injector.close();
            FakeEntityFactory.INSTANCE.getEntities().forEach(FakeEntity::remove);
        }
    }
}
