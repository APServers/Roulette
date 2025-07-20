package net.decentstudio.gamblingaddon.proxy;

import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

public class CommonProxy {



    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void openRouletteGui(EntityPlayer player, int balance) {

    }

    public void updateBets(List<PlayerBetDTO> bets) {

    }

    public void bet(EntityPlayer player, int chips, SectionColor color) {
    }

    public void updateBalance(int chips) {}
}
