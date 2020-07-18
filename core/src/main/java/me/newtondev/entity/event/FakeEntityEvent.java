package me.newtondev.entity.event;

import me.newtondev.entity.FakeEntity;
import org.bukkit.event.Event;

public abstract class FakeEntityEvent extends Event {

    private final FakeEntity entity;

    public FakeEntityEvent(FakeEntity entity) {
        this.entity = entity;
    }

    public FakeEntity getEntity() {
        return entity;
    }
}
