package net.decentstudio.gamblingaddon.network;

import io.netty.buffer.ByteBuf;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class S3PacketUpdateBalance implements IMessage {

    private int chips;

    public S3PacketUpdateBalance() {}

    public S3PacketUpdateBalance(int chips) {
        this.chips = chips;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.chips = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chips);
    }

    public static class Handler implements IMessageHandler<S3PacketUpdateBalance, IMessage> {

        @Override
        public IMessage onMessage(S3PacketUpdateBalance packet, MessageContext ctx) {
            GamblingAddon.proxy.updateBalance(packet.chips);
            return null;
        }
    }
}
