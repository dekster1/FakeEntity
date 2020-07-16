package me.newtondev.entity.packet;

import me.newtondev.entity.util.ReflectionUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class PacketContainer {

    private PacketType packet;
    private Set<Player> recipients;

    public PacketContainer(PacketType packet) {
        this.packet = packet;
        this.recipients = new HashSet<>();
    }

    public PacketContainer write(String argument, Object value) {
        Class<?> clazz = packet.getPacketClass();
        try {
            Field field = clazz.getDeclaredField(argument);
            field.setAccessible(true);
            field.set(getPacketObject(), value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public PacketContainer sendPacket(Player receiver) {
        recipients.add(receiver);
        ReflectionUtil.sendPacket(receiver, getPacketObject());

        return this;
    }

    public Class<?> getPacket() {
        return packet.getPacketClass();
    }

    public Set<Player> getRecipients() {
        return recipients;
    }

    private Object getPacketObject() {
        Object obj = null;
        try {
            obj = getPacket().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
