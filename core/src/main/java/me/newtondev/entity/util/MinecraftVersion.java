package me.newtondev.entity.util;

public enum MinecraftVersion {

    v1_8_R1(10801),
    v1_8_R2(10802),
    v1_8_R3(10803),
    v1_9_R1(10901),
    v1_9_R2(10902),
    v1_10_R1(101001),
    v1_11_R1(101101),
    v1_12_R1(101201),
    v1_13_R1(101301),
    v1_13_R2(101302),
    v1_14_R1(101401),
    v1_15_R1(101501),
    v1_16_R1(101601);

    int version;

    MinecraftVersion(int version) {
        this.version = version;
    }

    public boolean higherOrEqualsThan(String version) {
        return (this.toInt() >= MinecraftVersion.valueOf(version).toInt());
    }

    public boolean higherOrEqualsThan(MinecraftVersion version) {
        return (this.toInt() >= version.toInt());
    }

    public boolean lowerOrEqualsThan(String version) {
        return (this.toInt() <= MinecraftVersion.valueOf(version).toInt());
    }

    public boolean lowerThan(String version) {
        return (this.toInt() < MinecraftVersion.valueOf(version).toInt());
    }

    public int toInt() {
        return version;
    }
}
