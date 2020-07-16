package me.newtondev.entity;

import me.newtondev.entity.util.ReflectionUtil;
import me.newtondev.entity.validator.Legacy;

public enum FakeEntityType {

    ARMOR_STAND("EntityArmorStand"),
    ARROW("EntityArrow"),
    @Legacy(version = "v1_14_R1")
    BEE("EntityBee");

    String entity;

    FakeEntityType(String entity) {
        this.entity = entity;
    }

    public Class<?> getEntityClass() {
        return ReflectionUtil.getNMSClass(entity);
    }
}
