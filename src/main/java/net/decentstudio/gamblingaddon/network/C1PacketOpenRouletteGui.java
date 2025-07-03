package net.decentstudio.gamblingaddon.network;

import io.netty.buffer.ByteBuf;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C1PacketOpenRouletteGui implements IMessage {

    public C1PacketOpenRouletteGui() {}

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<C1PacketOpenRouletteGui, IMessage> {
        @Override
        public IMessage onMessage(C1PacketOpenRouletteGui packet, MessageContext ctx) {
            GamblingAddon.proxy.openRouletteGui();
            return null;
        }
    }
}
