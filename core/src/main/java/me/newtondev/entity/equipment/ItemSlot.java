package me.newtondev.entity.equipment;

import me.newtondev.entity.util.ReflectionUtil;

public enum ItemSlot {

    HEAD,
    CHEST,
    LEGS,
    FEET,
    HAND,
    MAINHAND,
    OFFHAND;

    public Object toNMS() {
        Class<?> enumItemSlot = ReflectionUtil.getNMSClass("EnumItemSlot");
        Object obj = null;
        try {
            obj = enumItemSlot.getField(this.name()).get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
