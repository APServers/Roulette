package net.decentstudio.gamblingaddon;

import net.decentstudio.gamblingaddon.network.C1PacketOpenRouletteGui;
import net.decentstudio.gamblingaddon.network.S1PacketOpenRouletteGui;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ua.byby.challengesaddon.database.Database;
import ua.byby.challengesaddon.network.*;
import net.decentstudio.gamblingaddon.proxy.CommonProxy;
import net.decentstudio.gamblingaddon.util.ui.font.Font;
import net.decentstudio.gamblingaddon.util.ui.font.FontContainer;

@Mod(modid = GamblingAddon.MODID,
        name = GamblingAddon.NAME,
        version = GamblingAddon.VERSION,
        dependencies = "required-after:narutocraft")
public class GamblingAddon
{
    public static final String MODID = "gamblingaddon";
    public static final String NAME = "NarutoCraft Gambling Addon";
    public static final String VERSION = "1.0";

    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MODID + "_mod");

    @SidedProxy(
        clientSide = "net.decentstudio.gamblingaddon.proxy.ClientProxy",
        serverSide = "net.decentstudio.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        registerModPackets();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
//        new ChallengesManager(challengesDAO).init();

        //event.registerServerCommand(new TestCommand());
    }

    private void registerModPackets() {
        GamblingAddon.NETWORK.registerMessage(C1PacketOpenRouletteGui.Handler.class,
                C1PacketOpenRouletteGui.class,
                0,
                Side.CLIENT);

        GamblingAddon.NETWORK.registerMessage(S1PacketOpenRouletteGui.Handler.class,
                S1PacketOpenRouletteGui.class,
                1,
                Side.SERVER);
    }
}
