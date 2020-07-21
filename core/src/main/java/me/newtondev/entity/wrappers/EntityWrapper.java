package me.newtondev.entity.wrappers;

import me.newtondev.entity.FakeEntityType;
import me.newtondev.entity.exception.InvalidVersionException;
import me.newtondev.entity.util.ReflectionUtil;
import me.newtondev.entity.util.access.FieldAccessor;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class EntityWrapper {

    private Object entity;
    private FakeEntityType type;
    private Class<?> clazz;

    private Object entityTypes;

    public EntityWrapper(FakeEntityType type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public void injectEntity(Location location) throws InvalidVersionException {
        if (isValid()) {
            boolean higher = ReflectionUtil.versionEqualsOrHigherThan("1_13");

            try {
                Object nmsWorld = location.getWorld().getClass().getMethod("getHandle").invoke(location.getWorld());
                Class<?> worldClass = ReflectionUtil.getNMSClass("World");
                Constructor<?> constructor;

                if (higher) {
                    Class<?> entityTypesClass = ReflectionUtil.getNMSClass("EntityTypes");
                    entityTypes = entityTypesClass.getField(type.name()).get(null);

                    constructor = clazz.getConstructor(entityTypesClass, worldClass);
                    entity = constructor.newInstance(entityTypes, nmsWorld);

                } else {
                    constructor = clazz.getConstructor(worldClass);
                    entity = constructor.newInstance(nmsWorld);
                }

                entity.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class)
                        .invoke(entity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidVersionException("Entity " + type.name() + " is not available for this server version.");
        }
    }

    public Object getEntityValue(String method) {
        Object value = null;
        try {
            value = entity.getClass().getMethod(method).invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return value;
    }

    public Object getEntity() {
        return entity;
    }

    private boolean isValid() {
        try {
            Field field = type.getClass().getField(type.name());
            if (!FieldAccessor.hasLegacy(field)) {
                return true;
            } else {
                if (ReflectionUtil.versionEqualsOrHigherThan(FieldAccessor.getLegacyVersion(field))) {
                    return true;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }
}
