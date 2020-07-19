package me.newtondev.entity.event;

import me.newtondev.entity.FakeEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class FakeEntityDeathEvent extends FakeEntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    public FakeEntityDeathEvent(FakeEntity entity) {
        super(entity);
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

}
