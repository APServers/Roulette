package net.decentstudio.gamblingaddon.service.impl;

import lombok.RequiredArgsConstructor;
import net.decentstudio.gamblingaddon.domain.Bid;
import net.decentstudio.gamblingaddon.domain.BidSection;
import net.decentstudio.gamblingaddon.domain.Gambler;
import net.decentstudio.gamblingaddon.domain.GameRoom;
import net.decentstudio.gamblingaddon.repository.BidRepository;
import net.decentstudio.gamblingaddon.repository.GamblerRepository;
import net.decentstudio.gamblingaddon.repository.GameRoomRepository;
import net.decentstudio.gamblingaddon.util.game.GameStatus;
import net.decentstudio.gamblingaddon.util.game.SectionColor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
public class RouletteGameService {
    private final GamblerRepository gamblerRepository;
    private final GameRoomRepository gameRoomRepository;
    private final BidRepository bidRepository;
    private final Random random = new Random();
    
    public void placeBid(UUID gamblerId, int gameRoomId, BidSection section, BigDecimal amount) {
        Optional<Gambler> gamblerOpt = gamblerRepository.findById(gamblerId);
        Optional<GameRoom> roomOpt = gameRoomRepository.findById(gameRoomId);
        
        if (!gamblerOpt.isPresent() || !roomOpt.isPresent()) {
            throw new IllegalArgumentException("Gambler or room not found");
        }
        
        Gambler gambler = gamblerOpt.get();
        GameRoom room = roomOpt.get();
        
        if (room.getStatus() != GameStatus.BETTING) {
            throw new IllegalStateException("Room not accepting bids");
        }
        
        if (gambler.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        
        // Deduct amount from gambler
        gambler.setBalance(gambler.getBalance().subtract(amount));
        
        // Create and save bid
        Bid bid = new Bid(UUID.randomUUID(), gamblerId, section, amount);
        room.getBids().add(bid);
        bidRepository.save(bid);
        gamblerRepository.save(gambler);
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
        Optional<Gambler> gamblerOpt = gamblerRepository.findById(bid.getGamblerId());
        if (!gamblerOpt.isPresent()) {
            return;
        }
        
        Gambler gambler = gamblerOpt.get();
        BigDecimal payout = calculatePayout(bid);
        gambler.setBalance(gambler.getBalance().add(payout));
        gamblerRepository.save(gambler);
    }
    
    private BigDecimal calculatePayout(Bid bid) {
        BidSection section = bid.getSection();
        
        // Straight number bet pays 35:1
        if (section.getNumber() != -1) {
            return bid.getAmount().multiply(BigDecimal.valueOf(35));
        }
        
        // Color bet pays 1:1
        if (section.getColor() != null) {
            return bid.getAmount().multiply(BigDecimal.valueOf(2)); // original + winnings
        }
        
        return BigDecimal.ZERO;
    }
    
    private SectionColor getColorForNumber(int number) {
        if (number == 0) return SectionColor.GREEN;
        
        // European roulette red numbers
        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        return Arrays.stream(redNumbers).anyMatch(n -> n == number) ? SectionColor.RED : SectionColor.BLACK;
    }
}