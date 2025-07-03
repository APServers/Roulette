package net.decentstudio.gamblingaddon.network;

import io.netty.buffer.ByteBuf;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.decentstudio.gamblingaddon.util.BuilderUtils;

public class S1PacketOpenRouletteGui implements IMessage {

    public S1PacketOpenRouletteGui() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<S1PacketOpenRouletteGui, IMessage> {

        @Override
        public IMessage onMessage(S1PacketOpenRouletteGui packet, MessageContext ctx) {
            if(!BuilderUtils.ISCLIENT) {
                GamblingAddon.NETWORK.sendTo(new C1PacketOpenRouletteGui(), ctx.getServerHandler().player);
            }

            return null;
        }
    }
}
