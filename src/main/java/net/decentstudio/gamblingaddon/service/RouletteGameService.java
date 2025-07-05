package net.decentstudio.gamblingaddon.service;

import net.decentstudio.gamblingaddon.domain.BidSection;
import java.math.BigDecimal;
import java.util.UUID;

public interface RouletteGameService {
    void placeBid(UUID gamblerId, int gameRoomId, BidSection section, BigDecimal amount);
    int spinWheel(int gameRoomId);
    void startNewGame(int gameRoomId);
}