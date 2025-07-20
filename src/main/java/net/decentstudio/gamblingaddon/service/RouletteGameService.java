package net.decentstudio.gamblingaddon.service;

import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public interface RouletteGameService {
    void placeBet(EntityPlayerMP player, Integer amount, SectionColor color);
    int spinWheel(int gameRoomId);
    void startNewGame(int gameRoomId);
    int findGameRoomId(EntityPlayerMP player);
    List<PlayerBetDTO> findBetsByGameRoomId(int gameRoomId);
}