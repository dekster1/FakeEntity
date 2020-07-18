package me.newtondev.entity.packet;

import me.newtondev.entity.util.ReflectionUtil;

public enum PacketType {
    // SERVER
    SPAWN_ENTITY("PacketPlayOutSpawnEntityLiving"),
    ENTITY_METADATA("PacketPlayOutEntityMetadata"),
    ENTITY_DESTROY("PacketPlayEntityDestroy"),
    ENTITY_EQUIPMENT("PacketPlayOutEntityEquipment"),
    ENTITY_TELEPORT("PacketPlayOutEntityTeleport"),
    // CLIENT
    USE_ENTITY("PacketPlayInUseEntity");

    private final String name;

    PacketType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Class<?> getPacketClass() {
        return ReflectionUtil.getNMSClass(name);
    }
}
