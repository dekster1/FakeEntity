package me.newtondev.entity;

import me.newtondev.entity.equipment.ItemSlot;
import me.newtondev.entity.event.FakeEntityDeathEvent;
import me.newtondev.entity.event.FakeEntitySpawnEvent;
import me.newtondev.entity.exception.InvalidVersionException;
import me.newtondev.entity.packet.PacketBuilder;
import me.newtondev.entity.packet.PacketListener;
import me.newtondev.entity.util.ReflectionUtil;
import me.newtondev.entity.wrappers.EntityWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class FakeEntity {

    private final FakeEntityType type;
    private Location location;
    private final EntityWrapper wrapper;
    private final Set<Player> viewers;

    public FakeEntity(FakeEntityType type) {
        this(type, null);
    }

    public FakeEntity(FakeEntityType type, Location location) {
        this.type = type;
        this.location = location;
        this.wrapper = new EntityWrapper(type, type.getEntityClass());
        this.viewers = new HashSet<>();
    }

    public FakeEntity spawn() {
        try {

            wrapper.injectEntity(location);
            Object packet = new PacketBuilder().buildPlayOutSpawnEntityLiving(wrapper.getEntity());
            updateMetadata();
            send(packet);

            if (FakeEntityFactory.INSTANCE.isRegistered()) {
                FakeEntityFactory.INSTANCE.addEntity(this);
                Bukkit.getPluginManager().callEvent(new FakeEntitySpawnEvent(this, location));
                viewers.forEach(PacketListener::registerListener);
            }
        } catch (InvalidVersionException e) {
            e.printStackTrace();
        }

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

    public FakeEntity removeViewer(Player player) {
        this.viewers.remove(player);
        Object packet = new PacketBuilder().buildPlayOutEntityDestroy(getEntityId());
        ReflectionUtil.sendPacket(player, packet);

        if (FakeEntityFactory.INSTANCE.isRegistered() && viewers.size() <= 0) {
            Bukkit.getPluginManager().callEvent(new FakeEntityDeathEvent(this));
        }

        return this;
    }

    public void updateMetadata() {
        Object packet = new PacketBuilder().buildPlayOutEntityMetadata(
                getEntityId(),
                wrapper.getEntityValue("getDataWatcher"),
                true);

        send(packet);
    }

    public void addEquipment(ItemSlot slot, ItemStack item) {
        Object packet = new PacketBuilder().buildPlayOutEntityEquipment(
                getEntityId(),
                slot, item);

        send(packet);
    }

    public void teleport(Location location) {
        teleport(location, true);
    }

    public void teleport(Location location, boolean onGround) {
        this.location = location;
        Object packet = new PacketBuilder().buildPlayOutEntityTeleport(
                getEntityId(),
                location, onGround);

        send(packet);
    }

    public void lookAt(Location location) {
        Location target = getLocation().setDirection(location.subtract(getLocation()).toVector());
        this.location = target;

        Object[] packets = new PacketBuilder().buildPlayOutEntityLook(getEntityId(),
                target.getYaw(), target.getPitch());

        send(packets[0]);
        send(packets[1]);
    }

    public void remove() {
        Object packet = new PacketBuilder().buildPlayOutEntityDestroy(getEntityId());
        send(packet);

        if (FakeEntityFactory.INSTANCE.isRegistered()) {
            FakeEntityFactory.INSTANCE.removeEntity(this);
            Bukkit.getPluginManager().callEvent(new FakeEntityDeathEvent(this));
        }
    }

    public Location getLocation() {
        return location;
    }

    public FakeEntityType getType() {
        return type;
    }

    public int getEntityId() {
        return (int) wrapper.getEntityValue("getId");
    }

    public Set<Player> getViewers() {
        return viewers;
    }

    private void send(Object packet) {
        viewers.forEach(p -> ReflectionUtil.sendPacket(p, packet));
    }

}
