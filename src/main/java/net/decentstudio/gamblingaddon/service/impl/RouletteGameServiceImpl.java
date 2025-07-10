package net.decentstudio.gamblingaddon.service.impl;

import lombok.RequiredArgsConstructor;
import net.decentstudio.gamblingaddon.domain.Bid;
import net.decentstudio.gamblingaddon.domain.BidSection;
import net.decentstudio.gamblingaddon.domain.GameRoom;
import net.decentstudio.gamblingaddon.integration.Integration;
import net.decentstudio.gamblingaddon.repository.BalanceRepository;
import net.decentstudio.gamblingaddon.repository.BidRepository;
import net.decentstudio.gamblingaddon.repository.GameRoomRepository;
import net.decentstudio.gamblingaddon.service.RouletteGameService;
import net.decentstudio.gamblingaddon.util.game.GameStatus;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
public class RouletteGameServiceImpl implements RouletteGameService {

    private final GameRoomRepository gameRoomRepository;
    private final BidRepository bidRepository;
    private final BalanceRepository balanceRepository;

    private final Random random = new Random();
    
    public synchronized void placeBid(EntityPlayer player, BidSection section, Integer amount) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Bid amount must be greater than zero");
        }

        int gameRoomId = Integration.integration.getCurrentGameRoomId(player.getName());
        Optional<GameRoom> roomOpt = gameRoomRepository.findById(gameRoomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }

        GameRoom room = roomOpt.get();
        
        if (room.getStatus() != GameStatus.BETTING) {
            throw new IllegalStateException("Room not accepting bids");
        }

        int chipsBalance = balanceRepository.findBalance(player)
                .orElseThrow(() -> new IllegalArgumentException("Player balance not found"));

        if (chipsBalance < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        
        balanceRepository.saveBalance(player, chipsBalance - amount);

        Bid bid = new Bid(UUID.randomUUID(), player, section, amount);
        room.getBids().add(bid);
        bidRepository.save(bid);
        gameRoomRepository.save(room);
    }
    
    public int spinWheel(int gameRoomId) {
        Optional<GameRoom> roomOpt = gameRoomRepository.findById(gameRoomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }
        
        GameRoom room = roomOpt.get();
        room.setStatus(GameStatus.STARTED);
        
        // Generate winning number (0-36)
        int winningNumber = random.nextInt(37);
        SectionColor winningColor = getColorForNumber(winningNumber);
        
        // Resolve bids
        for (Bid bid : room.getBids()) {
            if (isWinningBid(bid, winningNumber, winningColor)) {
                bid.setWinner(true);
                payoutWinner(bid);
            }
        }
        
        room.setStatus(GameStatus.FINISHED);
        gameRoomRepository.save(room);
        
        return winningNumber;
    }
    
    public void startNewGame(int gameRoomId) {
        Optional<GameRoom> roomOpt = gameRoomRepository.findById(gameRoomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }
        
        GameRoom room = roomOpt.get();
        room.getBids().clear();
        room.setStatus(GameStatus.BETTING);
        gameRoomRepository.save(room);
    }
    
    private boolean isWinningBid(Bid bid, int winningNumber, SectionColor winningColor) {
        BidSection section = bid.getSection();
        
        // Check number match
        if (section.getNumber() != -1 && section.getNumber() == winningNumber) {
            return true;
        }
        
        // Check color match
        if (section.getColor() != null && section.getColor() == winningColor) {
            return true;
        }
        
        return false;
    }
    
    private void payoutWinner(Bid bid) {
        if (bid.isWinner()) {
            return;
        }

        Integer balance = balanceRepository.findBalance(bid.getPlayer())
                .orElseThrow(() -> new IllegalArgumentException("Player balance not found"));

        Integer payout = calculatePayout(bid);
        balanceRepository.saveBalance(bid.getPlayer(), balance + payout);
    }
    
    private Integer calculatePayout(Bid bid) {
        BidSection section = bid.getSection();
        
        // Straight number bet pays 35:1
        if (section.getNumber() != -1) {
            return bid.getAmount() * 35;
        }
        
        // Color bet pays 1:1
        if (section.getColor() != null) {
            return bid.getAmount() * 2; // original + winnings
        }
        
        return 0;
    }
    
    private SectionColor getColorForNumber(int number) {
        if (number == 0) return SectionColor.GREEN;
        
        // European roulette red numbers
        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        return Arrays.stream(redNumbers).anyMatch(n -> n == number) ? SectionColor.RED : SectionColor.BLACK;
    }
}