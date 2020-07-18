package me.newtondev.entity.util;

import java.lang.reflect.Field;

public class AccessUtil {

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
}
