package me.newtondev.entity.util.access;

import me.newtondev.entity.Legacy;

import java.lang.reflect.Field;

public class FieldAccessor {

    public static void setValue(Object obj, String argument, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(argument);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getValue(Object obj, String argument) {
        try {
            Field field = obj.getClass().getDeclaredField(argument);
            field.setAccessible(true);

            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean hasLegacy(Field field) {
        return (field.isAnnotationPresent(Legacy.class));
    }

    public static String getLegacyVersion(Field field) {
        return (field.getAnnotation(Legacy.class).version());
    }
}
