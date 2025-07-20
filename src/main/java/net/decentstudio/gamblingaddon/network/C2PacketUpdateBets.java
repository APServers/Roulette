package net.decentstudio.gamblingaddon.network;

import io.netty.buffer.ByteBuf;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

public class C2PacketUpdateBets implements IMessage {

    private List<PlayerBetDTO> bets = new ArrayList<>();

    public C2PacketUpdateBets() {}

    public C2PacketUpdateBets(List<PlayerBetDTO> bets) {
        this.bets = bets;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            String nick = ByteBufUtils.readUTF8String(buf);
            int chips = buf.readInt();
            int sectionColorOrdinal = buf.readInt();
            int roomId = buf.readInt();

            SectionColor sectionColor = SectionColor.values()[sectionColorOrdinal];
            if (sectionColor == null) {
                return;
            }

            bets.add(new PlayerBetDTO(nick, chips, sectionColor, roomId));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(bets.size());
        for (PlayerBetDTO bet : bets) {
            ByteBufUtils.writeUTF8String(buf, bet.getNick());
            buf.writeInt(bet.getChips());
            buf.writeInt(bet.getSectionColor().ordinal());
            buf.writeInt(bet.getRoomId());
        }
    }

    public static class Handler implements IMessageHandler<C2PacketUpdateBets, IMessage> {
        @Override
        public IMessage onMessage(C2PacketUpdateBets packet, MessageContext ctx) {
            GamblingAddon.proxy.updateBets(packet.bets);
            return null;
        }
    }
}
