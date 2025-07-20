package net.decentstudio.gamblingaddon.proxy;

import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;
import net.decentstudio.gamblingaddon.network.S2PacketBet;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
//        new ChallengesManager();
    }

    @Override
    public void openRouletteGui(EntityPlayer player, int balance) {
        Minecraft.getMinecraft().addScheduledTask(() ->
                Minecraft.getMinecraft().displayGuiScreen(new GuiRoulette(balance)));
    }

    @Override
    public void updateBets(List<PlayerBetDTO> bets) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiRoulette) {
                ((GuiRoulette) Minecraft.getMinecraft().currentScreen).updateBets(bets);
            }
        });
    }

    @Override
    public void bet(EntityPlayer player, int chips, SectionColor color) {
        GamblingAddon.NETWORK.sendToServer(new S2PacketBet(chips, color));
    }

    @Override
    public void updateBalance(int chips) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiRoulette) {
                ((GuiRoulette) Minecraft.getMinecraft().currentScreen).updateBalance(chips);
            }
        });
    }
}
