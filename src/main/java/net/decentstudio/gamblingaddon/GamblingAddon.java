package net.decentstudio.gamblingaddon;

import lombok.Getter;
import net.decentstudio.gamblingaddon.network.*;
import net.decentstudio.gamblingaddon.proxy.CommonProxy;
import net.decentstudio.gamblingaddon.repository.BalanceRepository;
import net.decentstudio.gamblingaddon.repository.BidRepository;
import net.decentstudio.gamblingaddon.repository.GameRoomRepository;
import net.decentstudio.gamblingaddon.repository.impl.BalanceRepositoryImpl;
import net.decentstudio.gamblingaddon.repository.impl.BidRepositoryImpl;
import net.decentstudio.gamblingaddon.repository.impl.GameRoomRepositoryImpl;
import net.decentstudio.gamblingaddon.service.RouletteGameService;
import net.decentstudio.gamblingaddon.service.impl.RouletteGameServiceImpl;
import net.decentstudio.gamblingaddon.util.BuilderUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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
        serverSide = "net.decentstudio.gamblingaddon.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Getter
    private static BalanceRepository balanceRepository;
    @Getter
    private static BidRepository bidRepository;
    @Getter
    private static GameRoomRepository gameRoomRepository;

    @Getter
    private static RouletteGameService rouletteGameService;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (!BuilderUtils.ISCLIENT) {
            balanceRepository = new BalanceRepositoryImpl();
            bidRepository = new BidRepositoryImpl();
            gameRoomRepository = new GameRoomRepositoryImpl();

            rouletteGameService = new RouletteGameServiceImpl(
                    gameRoomRepository,
                    bidRepository,
                    balanceRepository
            );
        }

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

        event.registerServerCommand(new TestCommand());
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

        GamblingAddon.NETWORK.registerMessage(C2PacketUpdateBets.Handler.class,
                C2PacketUpdateBets.class,
                2,
                Side.CLIENT);

        GamblingAddon.NETWORK.registerMessage(S2PacketBet.Handler.class,
                S2PacketBet.class,
                3,
                Side.SERVER);

        GamblingAddon.NETWORK.registerMessage(S3PacketUpdateBalance.Handler.class,
                S3PacketUpdateBalance.class,
                4,
                Side.SERVER);
    }
}
