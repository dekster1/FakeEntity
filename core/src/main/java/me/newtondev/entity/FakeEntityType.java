package me.newtondev.entity;

import me.newtondev.entity.util.ReflectionUtil;
import me.newtondev.entity.validator.Legacy;

public enum FakeEntityType {

    ARMOR_STAND("EntityArmorStand"),
    BAT("EntityBat"),
    @Legacy(version = "1_15")
    BEE("EntityBee"),
    BLAZE("EntityBlaze"),
    CAT("EntityCat"),
    CAVE_SPIDER("EntityCaveSpider"),
    CHICKEN("EntityChicken"),
    @Legacy(version = "1_13")
    COD("EntityCod"),
    COW("EntityCow"),
    CREEPER("EntityCreeper"),
    @Legacy(version = "1_13")
    DOLPHIN("EntityDolphin"),
    DONKEY("EntityHorseDonkey"),
    @Legacy(version = "1_13")
    DROWNED("EntityDrowned"),
    ELDER_GUARDIAN("EntityGuardianElder"),
    ENDER_DRAGON("EntityEnderDragon"),
    ENDERMAN("EntityEnderman"),
    ENDERMITE("EntityEndermite"),
    @Legacy(version = "1_14")
    EVOKER("EntityEvoker"),
    @Legacy(version = "1_14")
    FOX("EntityFox"),
    GHAST("EntityGhast"),
    GIANT_ZOMBIE("EntityGiantZombie"),
    GUARDIAN("EntityGuardian"),
    @Legacy(version = "1_16")
    HOGLIN("EntityHoglin"),
    HORSE("EntityHorse"),
    @Legacy(version = "1_10")
    HUSK("EntityZombieHusk"),
    @Legacy(version = "1_12")
    ILLUSIONER("EntityIllagerIllusioner"),
    IRON_GOLEM("EntityIronGolem");


    private final String entity;

    FakeEntityType(String entity) {
        this.entity = entity;
    }

    public Class<?> getEntityClass() {
        return ReflectionUtil.getNMSClass(entity);
    }
}
