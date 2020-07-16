package me.newtondev.entity.item;

import me.newtondev.entity.validator.Legacy;

public enum ItemSlot {
    CHEST,
    FEET,
    HEAD,
    LEGS,
    MAINHAND,

    @Legacy(version = "v1_12")
    OFFHAND;

}
