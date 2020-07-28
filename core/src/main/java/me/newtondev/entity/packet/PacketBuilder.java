package me.newtondev.entity.packet;

import me.newtondev.entity.equipment.ItemSlot;
import me.newtondev.entity.query.Query;
import me.newtondev.entity.query.QueryResult;
import me.newtondev.entity.util.access.FieldAccessor;
import me.newtondev.entity.util.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;

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

            return constructor.newInstance((Object) new int[] {id});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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

    @Query(results = {"int", "EnumItemSlot"}, versions = {"v1_8_R3", "v1_9_R1"})
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
            FieldAccessor.setValue(obj, "a", id);
            FieldAccessor.setValue(obj, "b", location.getX());
            FieldAccessor.setValue(obj, "c", location.getY());
            FieldAccessor.setValue(obj, "d", location.getZ());
            FieldAccessor.setValue(obj, "e", ((byte) (int) (location.getYaw() * 256F / 360F)));
            FieldAccessor.setValue(obj, "f", ((byte) (int) (location.getPitch() * 256F / 360F)));
            FieldAccessor.setValue(obj, "g", onGround);

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
            FieldAccessor.setValue(headRotation, "a", id);
            FieldAccessor.setValue(headRotation, "b", (byte) ((yaw % 360) * 256 / 360));
            Constructor<?> constructor = aClass.getConstructor(int.class, byte.class, byte.class, boolean.class);

            return new Object[] {constructor.newInstance(id, (byte) ((yaw % 360) * 256 / 360),
                    (byte) ((pitch % 360) * 256 / 360), false), headRotation};

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
