package net.decentstudio.gamblingaddon.proxy;

import net.decentstudio.gamblingaddon.repository.BalanceRepository;
import net.decentstudio.gamblingaddon.repository.BidRepository;
import net.decentstudio.gamblingaddon.repository.GameRoomRepository;
import net.decentstudio.gamblingaddon.repository.impl.BalanceRepositoryImpl;
import net.decentstudio.gamblingaddon.repository.impl.BidRepositoryImpl;
import net.decentstudio.gamblingaddon.repository.impl.GameRoomRepositoryImpl;
import net.decentstudio.gamblingaddon.service.RouletteGameService;
import net.decentstudio.gamblingaddon.service.impl.RouletteGameServiceImpl;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    private BalanceRepository balanceRepository;
    private BidRepository bidRepository;
    private GameRoomRepository gameRoomRepository;

    private RouletteGameService rouletteGameService;

    public void preInit(FMLPreInitializationEvent event) {
        balanceRepository = new BalanceRepositoryImpl();
        bidRepository = new BidRepositoryImpl();
        gameRoomRepository = new GameRoomRepositoryImpl();

        rouletteGameService = new RouletteGameServiceImpl(
                gameRoomRepository,
                bidRepository,
                balanceRepository
        );
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void openRouletteGui() {
    }
}
