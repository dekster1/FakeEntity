package me.newtondev.entity.packet.netty;

import io.netty.channel.Channel;
import me.newtondev.entity.util.access.FieldAccessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class ChannelInjector {

    private final Plugin plugin;
    private boolean closed = false;

    public ChannelInjector(Plugin plugin) {
        this.plugin = plugin;
    }

    public void injectPlayer(Player player) {
        Channel channel = getChannel(player);
        if (channel == null) {
            throw new NullPointerException("Couldn't get channel??");
        }

        if (channel.pipeline().get("fake_entity_interact") != null) {
            channel.pipeline().remove("fake_entity_interact");
        }

        channel.pipeline().addBefore("packet_handler", "fake_entity_interact",
                new Interceptor(player));
    }

    public void uninjectPlayer(Player player) {
        Channel channel = getChannel(player);
        channel.eventLoop().execute(() -> {
            if (channel.pipeline().get("fake_entity_interact") != null) {
                channel.pipeline().remove("fake_entity_interact");
            }
        });
    }

    public Channel getChannel(Player player) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);

            return (Channel) FieldAccessor.getValue(networkManager, "channel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        if (!closed) {
            closed = true;

            for (Player player : Bukkit.getOnlinePlayers()) {
                uninjectPlayer(player);
            }

            HandlerList.unregisterAll();
        }
    }
}
