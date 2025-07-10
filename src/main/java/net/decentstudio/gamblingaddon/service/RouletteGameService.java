package net.decentstudio.gamblingaddon.service;

import net.decentstudio.gamblingaddon.domain.BidSection;
import net.minecraft.entity.player.EntityPlayer;

import java.math.BigDecimal;
import java.util.UUID;

public interface RouletteGameService {
    void placeBid(EntityPlayer player, BidSection section, Integer amount);
    int spinWheel(int gameRoomId);
    void startNewGame(int gameRoomId);
}