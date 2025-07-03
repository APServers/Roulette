package net.decentstudio.gamblingaddon.proxy;

import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.minecraft.client.Minecraft;
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
    public void openRouletteGui() {
        Minecraft.getMinecraft().addScheduledTask(() ->
                Minecraft.getMinecraft().displayGuiScreen(new GuiRoulette()));
    }
}
