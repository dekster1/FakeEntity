package me.newtondev.entity.packet;

import me.newtondev.entity.util.ReflectionUtil;

public enum PacketType {
    SPAWN_ENTITY(ReflectionUtil.getNMSClass("PacketPlayOutSpawnEntityLiving")),
    ENTITY_DESTROY(ReflectionUtil.getNMSClass("PacketPlayEntityDestroy")),
    ENTITY_METADATA(ReflectionUtil.getNMSClass("PacketPlayOutEntityMetadata"));

    private final Class<?> packetClass;

    PacketType(Class<?> packetClass) {
        this.packetClass = packetClass;
    }

    public Class<?> getPacketClass() {
        return packetClass;
    }
}
