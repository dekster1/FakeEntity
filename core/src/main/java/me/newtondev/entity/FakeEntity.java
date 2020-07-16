package me.newtondev.entity;

import org.bukkit.Location;

public class FakeEntity {

    private FakeEntityType type;
    private Location location;

    public FakeEntity(FakeEntityType type) {
        this(type, null);
    }

    public FakeEntity(FakeEntityType type, Location location) {
        this.type = type;
        this.location = location;
    }

    public void spawn() {


    }

    public FakeEntity setLocation(Location location) {

        return this;
    }

    public FakeEntity set(String param, boolean value) {

        return this;
    }

    public void remove() {

    }

    public Location getLocation() {
        return location;
    }

}
