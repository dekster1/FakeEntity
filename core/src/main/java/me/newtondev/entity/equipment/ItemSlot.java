package me.newtondev.entity.equipment;

import me.newtondev.entity.Legacy;
import me.newtondev.entity.util.ReflectionUtil;

public enum ItemSlot {

    HEAD(4),
    CHEST(3),
    LEGS(2),
    FEET(1),
    @Legacy MAINHAND(0),
    @Legacy OFFHAND(0),

    HAND(0) {
        @Override
        public Object toNMS() {
            return 0;
        }
    };

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
}
