package net.decentstudio.gamblingaddon.network;

import io.netty.buffer.ByteBuf;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;
import net.decentstudio.gamblingaddon.repository.BalanceRepository;
import net.decentstudio.gamblingaddon.service.RouletteGameService;
import net.decentstudio.gamblingaddon.util.BuilderUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

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
                EntityPlayerMP player = ctx.getServerHandler().player;
                if (player == null) {
                    return null;
                }

                BalanceRepository balanceRepository = GamblingAddon.getBalanceRepository();
                RouletteGameService rouletteGameService = GamblingAddon.getRouletteGameService();
                System.out.println(balanceRepository + " " + rouletteGameService);
                System.out.println("test bal " + balanceRepository.findBalance(ctx.getServerHandler().player) + " " + ctx.getServerHandler().player);
                int balance = balanceRepository.findBalance(player).orElse(0);
                int roomId = rouletteGameService.findGameRoomId(player);
                List<PlayerBetDTO> bets = rouletteGameService.findBetsByGameRoomId(roomId);

                GamblingAddon.NETWORK.sendTo(new C1PacketOpenRouletteGui(balance, bets), player);
            }

            return null;
        }
    }
}
