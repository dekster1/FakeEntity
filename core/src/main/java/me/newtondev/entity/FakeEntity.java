package me.newtondev.entity;

import me.newtondev.entity.exception.InvalidVersionException;
import me.newtondev.entity.packet.PacketContainer;
import me.newtondev.entity.packet.PacketType;
import me.newtondev.entity.wrappers.EntityWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class FakeEntity {

    private FakeEntityType type;
    private Location location;
    private EntityWrapper wrapper;
    private Set<Player> viewers;

    public FakeEntity(FakeEntityType type) {
        this(type, null);
    }

    public FakeEntity(FakeEntityType type, Location location) {
        this.type = type;
        this.location = location;
        this.viewers = new HashSet<>();
    }

    public void spawn() {
        wrapper = new EntityWrapper(type, type.getEntityClass());

        try {
            wrapper.injectEntity(location);
        } catch (InvalidVersionException e) {
            e.printStackTrace();
        }

        PacketContainer packet = new PacketContainer(PacketType.SPAWN_ENTITY)
                .write("a", wrapper.getEntityValue("getId"));

        this.viewers.forEach(packet::sendPacket);
    }

    public FakeEntity setLocation(Location location) {
        this.location = location;

        return this;
    }

    public FakeEntity addViewer(Player player) {
        this.viewers.add(player);

        return this;
    }

    public FakeEntity set(String param, boolean value) {

        return this;
    }

    /*public void updateMetadata() {
        PacketContainer<?> packet = new PacketContainer<>(PacketType.ENTITY_METADATA,
                wrapper.getEntityValue("getId"),
                wrapper.getEntityValue("getDataWatcher"),
                true);

        viewers.forEach(packet::sendPacket);
    }*/

    /*public void addEquipment() {
        PacketContainer packet = new PacketContainer(PacketType.ENTITY_EQUIPMENT)
                .write("a", wrapper.getEntityValue("getId"))
                .write("b", wrapper.getEntityValue("getDataWatcher"))
                .write("c", false);

        viewers.forEach(packet::sendPacket);
    }*/

    public void remove() {
        PacketContainer packet = new PacketContainer(PacketType.ENTITY_DESTROY)
                .write("a", wrapper.getEntityValue("getId"));

        viewers.forEach(packet::sendPacket);
    }

    public Location getLocation() {
        return location;
    }

    public Set<Player> getViewers() {
        return viewers;
    }

}
