package me.newtondev.entity.packet;

import me.newtondev.entity.equipment.ItemSlot;
import me.newtondev.entity.util.AccessUtil;
import me.newtondev.entity.util.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
* Just a little packet builder with all the nms
* attributes.
*
* @author 1iqintellectual
 */
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
            Constructor<?> constructor = aClass.getConstructors()[1];
            obj = constructor.newInstance(id);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public Object buildPlayOutEntityMetadata(int id, Object dataWatcher, boolean arg) {
        Object obj = null;
        Class<?> aClass = PacketType.ENTITY_METADATA.getPacketClass();
        try {
            Constructor<?> constructor = aClass.getConstructors()[1];
            obj = constructor.newInstance(id, dataWatcher, arg);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public Object buildPlayOutEntityEquipment(int id, ItemSlot slot, ItemStack item) {
        Object obj = null;

        Class<?> aClass = PacketType.ENTITY_EQUIPMENT.getPacketClass();
        Class<?> bClass = ReflectionUtil.getBukkitClass("inventory", "CraftItemStack");

        try {
            Object nmsItem = bClass.getMethod("asNMSCopy", ItemStack.class).invoke(
                    null, item);

            Constructor<?> constructor = aClass.getConstructors()[1];
            obj = constructor.newInstance(id, slot.toNMS(), nmsItem);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public Object buildPlayOutEntityTeleport(int id, Location location, boolean onGround) {
        Object obj = null;
        try {

            obj = PacketType.ENTITY_TELEPORT.getPacketClass().newInstance();

            AccessUtil.setValue(obj, "a", id);
            AccessUtil.setValue(obj, "b", location.getX());
            AccessUtil.setValue(obj, "c", location.getY());
            AccessUtil.setValue(obj, "d", location.getZ());
            AccessUtil.setValue(obj, "e", ((byte) (int) (location.getYaw() * 256F / 360F)));
            AccessUtil.setValue(obj, "f", ((byte) (int) (location.getPitch() * 256F / 360F)));
            AccessUtil.setValue(obj, "g", onGround);

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public Object[] buildPlayOutEntityLook(int id, float yaw, float pitch) {
        Class<?> aClass = PacketType.ENTITY_LOOK.getPacketClass();
        Class<?> bClass = PacketType.ENTITY_HEAD_ROTATION.getPacketClass();

        try {

            Object headRotation = bClass.newInstance();
            AccessUtil.setValue(headRotation, "a", id);
            AccessUtil.setValue(headRotation, "b", (byte) ((yaw % 360) * 256 / 360));
            Constructor<?> constructor = aClass.getConstructors()[1];

            return new Object[] {constructor.newInstance(id,
                    (byte) ((yaw % 360) * 256 / 360), (byte) ((pitch % 360) * 256 / 360), false),
                    headRotation};

        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
