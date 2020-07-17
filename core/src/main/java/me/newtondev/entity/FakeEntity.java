package me.newtondev.entity;

import me.newtondev.entity.exception.InvalidVersionException;
import me.newtondev.entity.packet.PacketBuilder;
import me.newtondev.entity.util.ReflectionUtil;
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
        this.wrapper = new EntityWrapper(type, type.getEntityClass());
    }

    public FakeEntity spawn() {
        try {
            wrapper.injectEntity(location);
        } catch (InvalidVersionException e) {
            e.printStackTrace();
        }

        Object packet = new PacketBuilder().buildPlayOutSpawnEntityLiving(wrapper.getEntity());
        updateMetadata();
        send(packet);

        return this;
    }

    public FakeEntity setLocation(Location location) {
        this.location = location;

        return this;
    }

    public FakeEntity addViewer(Player player) {
        this.viewers.add(player);

        return this;
    }

    public FakeEntity setAttribute(String param, boolean value) {

        return this;
    }

    public void updateMetadata() {
        Object packet = new PacketBuilder().buildPlayOutEntityMetadata(
                (int) wrapper.getEntityValue("getId"),
                wrapper.getEntityValue("getDataWatcher"),
                false);

        send(packet);
    }

    /*public void addEquipment() {
        PacketContainer packet = new PacketContainer(PacketType.ENTITY_EQUIPMENT)
                .write("a", wrapper.getEntityValue("getId"))
                .write("b", wrapper.getEntityValue("getDataWatcher"))
                .write("c", false);

        viewers.forEach(packet::sendPacket);
    }*/

    public void remove() {
        Object packet = new PacketBuilder().buildPlayOutEntityDestroy(
                (int) wrapper.getEntityValue("getId"));

        send(packet);
    }

    public Location getLocation() {
        return location;
    }

    public Set<Player> getViewers() {
        return viewers;
    }

    private void send(Object packet) {
        viewers.forEach(p -> ReflectionUtil.sendPacket(p, packet));
    }

}
