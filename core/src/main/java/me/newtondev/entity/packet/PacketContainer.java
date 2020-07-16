package me.newtondev.entity.packet;

import me.newtondev.entity.util.ReflectionUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class PacketContainer {

    private final PacketType packet;
    private final Set<Player> recipients;
    private Object packetObject;

    public PacketContainer(PacketType packet, Object... parameters) {
        this.packet = packet;
        this.recipients = new HashSet<>();

        Constructor<?> constructor = getPacket().getDeclaredConstructors()[0];
        try {
            packetObject = constructor.newInstance((Object) parameters);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Player receiver) {
        recipients.add(receiver);
        ReflectionUtil.sendPacket(receiver, getPacketObject());

    }

    public Class<?> getPacket() {
        return packet.getPacketClass();
    }

    public Set<Player> getRecipients() {
        return recipients;
    }

    public Object getPacketObject() {
        return packetObject;
    }
}
