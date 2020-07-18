package me.newtondev.entity.event;

import me.newtondev.entity.FakeEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class FakeEntityInteractEvent extends FakeEntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private boolean cancelled = false;

    public FakeEntityInteractEvent(FakeEntity entity, Player player) {
        super(entity);
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }
}
