package me.newtondev.entity;

import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public enum FakeEntityFactory {

    INSTANCE;

    private Plugin plugin;

    public void register(Plugin plugin) {
        this.plugin = plugin;
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
}
