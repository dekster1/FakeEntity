package me.newtondev.entity.equipment;

import me.newtondev.entity.Legacy;
import me.newtondev.entity.util.ReflectionUtil;
import me.newtondev.entity.util.access.FieldAccessor;

import java.lang.reflect.Field;

public enum ItemSlot {

    HEAD(4),
    CHEST(3),
    LEGS(2),
    FEET(1),
    HAND(0),
    @Legacy MAINHAND(0),
    @Legacy OFFHAND(0);

    private final int slot;

    ItemSlot(int slot) {
        this.slot = slot;
    }

    public int toInt() {
        return slot;
    }

    public Object toNMS() {
        Class<?> enumItemSlot = ReflectionUtil.getNMSClass("EnumItemSlot");
        try {
            return enumItemSlot.getField(this.name()).get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isCompatible() {
        try {
            Field field = this.getClass().getField(this.name());
            return (FieldAccessor.hasLegacy(field) &&
                    ReflectionUtil.versionEqualsOrHigherThan(FieldAccessor.getLegacyVersion(field)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
