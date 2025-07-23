package net.decentstudio.gamblingaddon.network;

import io.netty.buffer.ByteBuf;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.util.BuilderUtils;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class S2PacketBet implements IMessage {

    private int chips;
    private SectionColor color;

    public S2PacketBet() {}

    public S2PacketBet(int chips, SectionColor color) {
        this.chips = chips;
        this.color = color;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.chips = buf.readInt();
        this.color = SectionColor.values()[buf.readByte()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chips);
        buf.writeByte(color.ordinal());
    }

    public static class Handler implements IMessageHandler<S2PacketBet, IMessage> {

        @Override
        public IMessage onMessage(S2PacketBet packet, MessageContext ctx) {
            if (!BuilderUtils.ISCLIENT) {
                EntityPlayerMP player = ctx.getServerHandler().player;
                if (player == null) {
                    return null;
                }

                int chips = packet.chips;
                SectionColor color = packet.color;

                if (chips <= 0 || color == null) {
                    return null;
                }

                try {
                    GamblingAddon.getRouletteGameService().placeBet(player, chips, color);
                } catch (Exception e) {
                    System.out.println("Failed to place bet: " + e.getMessage());
                    return null;
                }
            }

            return null;
        }
    }
}
