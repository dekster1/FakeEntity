package me.newtondev.entity.wrappers;

import me.newtondev.entity.FakeEntityType;
import me.newtondev.entity.exception.InvalidVersionException;
import me.newtondev.entity.util.ReflectionUtil;
import me.newtondev.entity.validator.Legacy;
import org.bukkit.Location;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class EntityWrapper {

    private Object entity;
    private FakeEntityType type;
    private Class<?> clazz;

    private Object entityTypes;
    private Class<?> entityTypesClass;
    private boolean higher = ReflectionUtil.versionEqualsOrHigherThan("v1_13");

    public EntityWrapper(FakeEntityType type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public void injectEntity(Location location) throws InvalidVersionException {
        if (isValid()) {
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
            try {
                if (higher) {
                    entityTypesClass = ReflectionUtil.getNMSClass("EntityTypes");
                    entityTypes = entityTypesClass.getField(type.name()).get(null);
                }

                Object nmsWorld = location.getWorld().getClass().getMethod("getHandle").invoke(location.getWorld());

                entity = (higher) ? constructor.newInstance(entityTypes, nmsWorld) :
                        constructor.newInstance(nmsWorld);

                // Support for pitch and yaw will come soon, since there are different methods from 1.8 to 1.15
                entity.getClass().getMethod("setPosition", double.class, double.class, double.class)
                        .invoke(entity, location.getX(), location.getY(), location.getZ());

            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException
                    | NoSuchFieldException | InstantiationException e) {
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
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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
            Annotation annotation = field.getAnnotation(Legacy.class);
            if (annotation == null) {
                return true;
            } else {
                if (ReflectionUtil.versionEqualsOrHigherThan(getVersion(field))) {
                    return true;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String getVersion(Field field) {
        return field.getAnnotation(Legacy.class).version();
    }

}
