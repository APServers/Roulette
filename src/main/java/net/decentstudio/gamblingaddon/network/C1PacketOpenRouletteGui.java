package net.decentstudio.gamblingaddon.network;

import io.netty.buffer.ByteBuf;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class C1PacketOpenRouletteGui implements IMessage {

    private int balance;
    private List<PlayerBetDTO> bets;

    public C1PacketOpenRouletteGui() {}

    public C1PacketOpenRouletteGui(int balance, List<PlayerBetDTO> bets) {
        this.balance = balance;
        this.bets = bets;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.balance = buf.readInt();
        int size = buf.readInt();
        if (size <= 0) {
            return;
        }

        for (int i = 0; i < size; i++) {
            String nick = ByteBufUtils.readUTF8String(buf);
            int chips = buf.readInt();
            int sectionColorOrdinal = buf.readInt();
            int roomId = buf.readInt();

            SectionColor sectionColor = SectionColor.values()[sectionColorOrdinal];
            if (sectionColor == null) {
                return;
            }

            PlayerBetDTO bet = new PlayerBetDTO(nick, chips, sectionColor, roomId);
            bets.add(bet);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(balance);
        buf.writeInt(bets == null ? 0 : bets.size());
        for (PlayerBetDTO bet : bets) {
            ByteBufUtils.writeUTF8String(buf, bet.getNick());
            buf.writeInt(bet.getChips());
            buf.writeInt(bet.getSectionColor().ordinal());
            buf.writeInt(bet.getRoomId());
        }
    }

    public static class Handler implements IMessageHandler<C1PacketOpenRouletteGui, IMessage> {
        @Override
        public IMessage onMessage(C1PacketOpenRouletteGui packet, MessageContext ctx) {
            GamblingAddon.proxy.openRouletteGui(null, packet.balance);
            return null;
        }
    }
}
