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
    IRON_GOLEM("EntityIronGolem"),
    @Legacy(version = "1_11")
    LLAMA("EntityLlama"),
    MAGMA_CUBE("EntityMagmaCube"),
    MUSHROOM_COW("EntityMushroomCow"),
    MULE("EntityHorseMule"),
    OCELOT("EntityOcelot"),
    @Legacy(version = "1_14")
    PANDA("EntityPanda"),
    @Legacy(version = "1_12")
    PARROT("EntityParrot"),
    @Legacy(version = "1_13")
    PHANTOM("EntityPhantom"),
    PIG("EntityPig"),
    @Legacy(version = "1_16")
    PIGGLIN("EntityPigglin"),
    @Legacy(version = "1_14")
    PILLAGER("EntityPillager"),
    @Legacy(version = "1_10")
    POLAR_BEAR("EntityPolarBear"),
    @Legacy(version = "1_16")
    PIGLIN("EntityPiglin"),
    PIG_ZOMBIE("EntityPigZombie"),
    @Legacy(version = "1_13")
    PUFFERFISH("EntityPufferFish"),
    RABBIT("EntityRabbit"),
    @Legacy(version = "1_14")
    RAVAGER("EntityRavager"),
    @Legacy(version = "1_13")
    SALMON("EntitySalmon"),
    SHEEP("EntitySheep"),
    @Legacy(version = "1_9")
    SHULKER("EntityShulker"),
    SILVERFISH("EntitySilverfish"),
    SKELETON("EntitySkeleton"),
    SKELETON_HORSE("EntityHorseSkeleton"),
    SLIME("EntitySlime"),
    SNOW_MAN("EntitySnowman"),
    SPIDER("EntitySpider"),
    SQUID("EntitySquid"),
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
    VILLAGER("EntityVillager"),
    @Legacy(version = "1_11")
    VINDICATOR("EntityVindicator"),
    @Legacy(version = "1_14")
    WANDERING_TRADER("EntityVillagerTrader"),
    WITCH("EntityWitch"),
    WITHER("EntityWither"),
    WITHER_SKELETON("EntitySkeletonWither"),
    WOLF("EntityWolf"),
    @Legacy(version = "1_16")
    ZOGLIN("EntityZoglin"),
    ZOMBIE("EntityZombie"),
    ZOMBIE_VILLAGER("EntityZombieVillager");

    private final String entity;

    FakeEntityType(String entity) {
        this.entity = entity;
    }

    public Class<?> getEntityClass() {
        return ReflectionUtil.getNMSClass(entity);
    }
}
