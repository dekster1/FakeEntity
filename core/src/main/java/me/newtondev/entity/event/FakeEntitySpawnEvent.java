package me.newtondev.entity.event;

import me.newtondev.entity.FakeEntity;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class FakeEntitySpawnEvent extends FakeEntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Location location;
    private boolean cancelled = false;

    public FakeEntitySpawnEvent(FakeEntity entity, Location location) {
        super(entity);
        this.location = location;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Location getLocation() {
        return location;
    }
}
