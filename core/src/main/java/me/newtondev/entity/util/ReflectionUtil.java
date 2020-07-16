package me.newtondev.entity.util;

import org.bukkit.Bukkit;

public class ReflectionUtil {

    private static final String VERSION;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf('.') + 1);
        VERSION = version;
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static boolean versionEqualsOrHigherThan(String value) {
        String[] values = VERSION.split("_");
        String[] diff = value.split("_");

        return (Integer.parseInt(values[1]) >= Integer.parseInt(diff[1]));
    }

    public static String getVersion() {
        return VERSION;
    }
}
