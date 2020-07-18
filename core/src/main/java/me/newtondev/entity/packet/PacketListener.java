package me.newtondev.entity.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.newtondev.entity.FakeEntity;
import me.newtondev.entity.FakeEntityFactory;
import me.newtondev.entity.query.Query;
import me.newtondev.entity.query.QueryResult;
import me.newtondev.entity.event.FakeEntityInteractEvent;
import me.newtondev.entity.util.AccessUtil;
import me.newtondev.entity.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PacketListener extends ChannelInboundHandlerAdapter {

    private final Player player;

    public PacketListener(Player player) {
        this.player = player;
    }

    @Query(result = {"a", "b"}, version = "1_13")
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Class<?> packetClass = PacketType.USE_ENTITY.getPacketClass();

        if (msg.getClass().equals(packetClass)) {

            Class<?> bClass = ReflectionUtil.getNMSClass(packetClass.getSimpleName() + "$EnumEntityUseAction");
            Object obj = msg.getClass().getMethod(new QueryResult(this.getClass()).getResult()).invoke(msg);
            Object enumObject = bClass.getField("INTERACT_AT").get(null);

            Bukkit.getServer().getScheduler()
                    .runTask(FakeEntityFactory.INSTANCE.getPlugin(), () -> {
                        if (obj == enumObject) {

                            FakeEntityFactory.INSTANCE.getEntities().forEach(en -> {
                                if (en.getId() == (int) AccessUtil.getValue(msg, "a")
                                        && en.getViewers().contains(player)) {

                                    Bukkit.getPluginManager().callEvent(new FakeEntityInteractEvent(en, player));
                                }
                            });
                        }
                    });
        }
        super.channelRead(ctx, msg);
    }

    public static void registerListener(Player player) {
        Channel c = getChannel(player);
        if (c == null) {
            throw new NullPointerException("Couldn't get channel??");
        }
        c.pipeline().addBefore("packet_handler", "packet_in_listener",
                new PacketListener(player));
    }

    public static Channel getChannel(Player player) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);

            Field field = networkManager.getClass().getDeclaredField("channel");
            field.setAccessible(true);

            Channel c = (Channel) field.get(networkManager);
            field.setAccessible(false);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
