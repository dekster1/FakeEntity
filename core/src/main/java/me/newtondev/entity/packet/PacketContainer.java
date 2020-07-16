package me.newtondev.entity.packet;

import java.lang.reflect.Field;

public class PacketContainer {

    private Class<?> packet;

    public PacketContainer(Class<?> packet) {
        this.packet = packet;
    }

    public void write(String argument, Object value) {
        try {
            Field field = packet.getDeclaredField(argument);
            field.setAccessible(true);
            field.set(argument, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
