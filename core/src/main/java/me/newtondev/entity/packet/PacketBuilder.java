package me.newtondev.entity.packet;

import me.newtondev.entity.equipment.ItemSlot;
import me.newtondev.entity.query.Query;
import me.newtondev.entity.query.QueryResult;
import me.newtondev.entity.util.AccessUtil;
import me.newtondev.entity.util.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;

/*
* Just a little packet builder with all the nms
* attributes.
*
* @author 1iqintellectual
 */
public class PacketBuilder {

    public Object buildPlayOutSpawnEntityLiving(Object entity) {
        try {
            Class<?> aClass = PacketType.SPAWN_ENTITY.getPacketClass();
            Class<?> bClass = ReflectionUtil.getNMSClass("EntityLiving");
            Constructor<?> constructor = aClass.getConstructor(bClass);

            return constructor.newInstance(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object buildPlayOutEntityDestroy(int id) {
        try {
            Class<?> aClass = PacketType.ENTITY_DESTROY.getPacketClass();
            Constructor<?> constructor = aClass.getConstructors()[1];

            return constructor.newInstance(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object buildPlayOutEntityMetadata(int id, Object dataWatcher, boolean arg) {
        try {
            Class<?> aClass = PacketType.ENTITY_METADATA.getPacketClass();
            Class<?> bClass = ReflectionUtil.getNMSClass("DataWatcher");
            Constructor<?> constructor = aClass.getConstructor(int.class, bClass, boolean.class);

            return constructor.newInstance(id, dataWatcher, arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Query(result = {"int", "EnumItemSlot"}, version = "1_9")
    public Object buildPlayOutEntityEquipment(int id, ItemSlot slot, ItemStack item) {
        try {
            String result = new QueryResult(this.getClass()).getResult();
            Class<?> aClass = PacketType.ENTITY_EQUIPMENT.getPacketClass();
            Class<?> bClass = ReflectionUtil.getBukkitClass("inventory", "CraftItemStack");
            Class<?> cClass = ReflectionUtil.getNMSClass("ItemStack");

            Constructor<?> constructor;
            Object nmsItem = bClass.getMethod("asNMSCopy", ItemStack.class).invoke(
                    null, item);

            if (result.equals("int")) {
                constructor = aClass.getConstructor(int.class, int.class, cClass);
                return constructor.newInstance(id, slot.toInt(), nmsItem);

            } else {
                constructor = aClass.getConstructor(int.class, ReflectionUtil.getNMSClass(result), cClass);
                return constructor.newInstance(id, slot.toNMS(), nmsItem);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object buildPlayOutEntityTeleport(int id, Location location, boolean onGround) {
        try {

            Object obj = PacketType.ENTITY_TELEPORT.getPacketClass().newInstance();
            AccessUtil.setValue(obj, "a", id);
            AccessUtil.setValue(obj, "b", location.getX());
            AccessUtil.setValue(obj, "c", location.getY());
            AccessUtil.setValue(obj, "d", location.getZ());
            AccessUtil.setValue(obj, "e", ((byte) (int) (location.getYaw() * 256F / 360F)));
            AccessUtil.setValue(obj, "f", ((byte) (int) (location.getPitch() * 256F / 360F)));
            AccessUtil.setValue(obj, "g", onGround);

            return obj;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object[] buildPlayOutEntityLook(int id, float yaw, float pitch) {
        try {

            Class<?> aClass = PacketType.ENTITY_LOOK.getPacketClass();
            Class<?> bClass = PacketType.ENTITY_HEAD_ROTATION.getPacketClass();

            Object headRotation = bClass.newInstance();
            AccessUtil.setValue(headRotation, "a", id);
            AccessUtil.setValue(headRotation, "b", (byte) ((yaw % 360) * 256 / 360));
            Constructor<?> constructor = aClass.getConstructor(int.class, byte.class, byte.class, boolean.class);

            return new Object[] {constructor.newInstance(id, (byte) ((yaw % 360) * 256 / 360),
                    (byte) ((pitch % 360) * 256 / 360), false), headRotation};

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
