package me.newtondev.entity.packet.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.newtondev.entity.FakeEntityFactory;
import me.newtondev.entity.event.FakeEntityInteractEvent;
import me.newtondev.entity.packet.PacketType;
import me.newtondev.entity.query.Query;
import me.newtondev.entity.query.QueryResult;
import me.newtondev.entity.util.ReflectionUtil;
import me.newtondev.entity.util.access.FieldAccessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Interceptor extends ChannelInboundHandlerAdapter {

    private final Player player;
    private long time;

    public Interceptor(Player player) {
        this.player = player;
    }

    @Override @Query(result = {"a", "b"})
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object packet = PacketType.USE_ENTITY.getPacketClass().newInstance();

        if (msg.getClass().isInstance(packet)) {

            Class<?> bClass = ReflectionUtil.getNMSClass(packet.getClass().getSimpleName() + "$EnumEntityUseAction");
            Object obj = msg.getClass().getMethod(new QueryResult(this.getClass()).getResult()).invoke(msg);
            Object enumObject = bClass.getField("INTERACT_AT").get(null);

            Bukkit.getServer().getScheduler().runTask(FakeEntityFactory.INSTANCE.getPlugin(), () -> {
                if (obj == enumObject) {

                    FakeEntityFactory.INSTANCE.getEntities().forEach(en -> {
                        if (en.getEntityId() == (int) FieldAccessor.getValue(msg, "a")
                                && en.getViewers().contains(player) && canInteract()) {

                            time = System.currentTimeMillis() + 500;
                            Bukkit.getPluginManager().callEvent(new FakeEntityInteractEvent(en, player));
                        }
                    });
                }
            });
        }

        super.channelRead(ctx, msg);
    }

    private boolean canInteract() {
        return (time - System.currentTimeMillis() <= 0);
    }
}
