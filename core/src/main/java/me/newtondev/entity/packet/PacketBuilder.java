package me.newtondev.entity.packet;

import me.newtondev.entity.util.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PacketBuilder {

    public Object buildPlayOutSpawnEntityLiving(Object entity) {
        Object obj = null;
        Class<?> aClass = PacketType.SPAWN_ENTITY.getPacketClass();
        Class<?> bClass = ReflectionUtil.getNMSClass("EntityLiving");
        try {
            Constructor<?> constructor = aClass.getConstructor(bClass);
            obj = constructor.newInstance(entity);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public Object buildPlayOutEntityDestroy(int id) {
        Object obj = null;
        Class<?> aClass = PacketType.ENTITY_DESTROY.getPacketClass();
        try {
            Constructor<?> constructor = aClass.getConstructor(int.class);
            obj = constructor.newInstance(id);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public Object buildPlayOutEntityMetadata(int id, Object dataWatcher, boolean arg) {
        Object obj = null;
        Class<?> aClass = PacketType.ENTITY_METADATA.getPacketClass();
        try {
            Constructor<?> constructor = aClass.getConstructors()[0];
            obj = constructor.newInstance(id, dataWatcher, arg);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
