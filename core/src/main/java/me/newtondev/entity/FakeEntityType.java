package me.newtondev.entity;

import me.newtondev.entity.util.ReflectionUtil;

public enum FakeEntityType {

    ARMOR_STAND("EntityArmorStand"),
    BAT("EntityBat"),
    BLAZE("EntityBlaze"),
    CAT("EntityCat"),
    CAVE_SPIDER("EntityCaveSpider"),
    CHICKEN("EntityChicken"),
    COW("EntityCow"),
    CREEPER("EntityCreeper"),
    DONKEY("EntityHorseDonkey"),
    ELDER_GUARDIAN("EntityGuardianElder"),
    ENDER_DRAGON("EntityEnderDragon"),
    ENDERMAN("EntityEnderman"),
    ENDERMITE("EntityEndermite"),
    GHAST("EntityGhast"),
    GIANT_ZOMBIE("EntityGiantZombie"),
    GUARDIAN("EntityGuardian"),
    HORSE("EntityHorse"),
    IRON_GOLEM("EntityIronGolem"),
    MAGMA_CUBE("EntityMagmaCube"),
    MUSHROOM_COW("EntityMushroomCow"),
    MULE("EntityHorseMule"),
    OCELOT("EntityOcelot"),
    PIG("EntityPig"),
    PIG_ZOMBIE("EntityPigZombie"),
    RABBIT("EntityRabbit"),
    SHEEP("EntitySheep"),
    SILVERFISH("EntitySilverfish"),
    SKELETON("EntitySkeleton"),
    SKELETON_HORSE("EntityHorseSkeleton"),
    SLIME("EntitySlime"),
    SNOW_MAN("EntitySnowman"),
    SPIDER("EntitySpider"),
    SQUID("EntitySquid"),
    VILLAGER("EntityVillager"),
    WITCH("EntityWitch"),
    WITHER("EntityWither"),
    WITHER_SKELETON("EntitySkeletonWither"),
    WOLF("EntityWolf"),
    ZOMBIE("EntityZombie"),
    ZOMBIE_VILLAGER("EntityZombieVillager"),
    @Legacy(version = "1_15")
    BEE("EntityBee"),
    @Legacy(version = "1_13")
    COD("EntityCod"),
    @Legacy(version = "1_13")
    DOLPHIN("EntityDolphin"),
    @Legacy(version = "1_13")
    DROWNED("EntityDrowned"),
    @Legacy(version = "1_14")
    EVOKER("EntityEvoker"),
    @Legacy(version = "1_14")
    FOX("EntityFox"),
    @Legacy(version = "1_16")
    HOGLIN("EntityHoglin"),
    @Legacy(version = "1_10")
    HUSK("EntityZombieHusk"),
    @Legacy(version = "1_12")
    ILLUSIONER("EntityIllagerIllusioner"),
    @Legacy(version = "1_11")
    LLAMA("EntityLlama"),
    @Legacy(version = "1_14")
    PANDA("EntityPanda"),
    @Legacy(version = "1_12")
    PARROT("EntityParrot"),
    @Legacy(version = "1_13")
    PHANTOM("EntityPhantom"),
    @Legacy(version = "1_16")
    PIGGLIN("EntityPigglin"),
    @Legacy(version = "1_14")
    PILLAGER("EntityPillager"),
    @Legacy(version = "1_10")
    POLAR_BEAR("EntityPolarBear"),
    @Legacy(version = "1_16")
    PIGLIN("EntityPiglin"),
    @Legacy(version = "1_13")
    PUFFERFISH("EntityPufferFish"),
    @Legacy(version = "1_14")
    RAVAGER("EntityRavager"),
    @Legacy(version = "1_13")
    SALMON("EntitySalmon"),
    @Legacy(version = "1_9")
    SHULKER("EntityShulker"),
    @Legacy(version = "1_10")
    STRAY("EntitySkeletonStray"),
    @Legacy(version = "1_16")
    STRIDER("EntityStrider"),
    @Legacy(version = "1_14")
    TRADER_LLAMA("EntityLlamaTrader"),
    @Legacy(version = "1_13")
    TROPICAL_FISH("EntityTropicalFish"),
    @Legacy(version = "1_13")
    TURTLE("EntityTurtle"),
    @Legacy(version = "1_11")
    VEX("EntityVex"),
    @Legacy(version = "1_11")
    VINDICATOR("EntityVindicator"),
    @Legacy(version = "1_14")
    WANDERING_TRADER("EntityVillagerTrader"),
    @Legacy(version = "1_16")
    ZOGLIN("EntityZoglin");

    private final String entity;

    FakeEntityType(String entity) {
        this.entity = entity;
    }

    public Class<?> getEntityClass() {
        return ReflectionUtil.getNMSClass(entity);
    }
}
