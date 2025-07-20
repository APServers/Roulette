package net.decentstudio.gamblingaddon.service.impl;

import lombok.RequiredArgsConstructor;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.domain.Bet;
import net.decentstudio.gamblingaddon.domain.GameRoom;
import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;
import net.decentstudio.gamblingaddon.integration.Integration;
import net.decentstudio.gamblingaddon.mapper.BetMapper;
import net.decentstudio.gamblingaddon.network.C2PacketUpdateBets;
import net.decentstudio.gamblingaddon.repository.BalanceRepository;
import net.decentstudio.gamblingaddon.repository.BidRepository;
import net.decentstudio.gamblingaddon.repository.GameRoomRepository;
import net.decentstudio.gamblingaddon.service.RouletteGameService;
import net.decentstudio.gamblingaddon.util.BuilderUtils;
import net.decentstudio.gamblingaddon.util.game.GameStatus;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RouletteGameServiceImpl implements RouletteGameService {

    private final GameRoomRepository gameRoomRepository;
    private final BidRepository bidRepository;
    private final BalanceRepository balanceRepository;

    private final Random random = new Random();
    
    public synchronized void placeBet(EntityPlayerMP player, Integer amount, SectionColor section) {
        if (!BuilderUtils.ISCLIENT) {
            return;
        }

        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Bid amount must be greater than zero");
        }

        int gameRoomId = findGameRoomId(player);
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

        Bet bid = new Bet(UUID.randomUUID(), player.getName(), player, section, amount);
        room.getBids().add(bid);
        bidRepository.save(bid);
        gameRoomRepository.save(room);

        updateBids(room);
    }

    private void updateBids(GameRoom room) {
        if (!BuilderUtils.ISCLIENT) {
            return;
        }

        List<PlayerBetDTO> bets = findBetsByGameRoomId(room.getId());
        for (Bet bet : room.getBids()) {
            GamblingAddon.NETWORK.sendTo(new C2PacketUpdateBets(bets), bet.getPlayer());
        }
    }
    
    public int spinWheel(int gameRoomId) {
        if (!BuilderUtils.ISCLIENT) {
            return -1;
        }

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
        for (Bet bid : room.getBids()) {
            if (isWinningBid(bid, winningNumber, winningColor)) {
                bid.setWinner(true);
                payoutWinner(bid);
            }
        }
        
        room.setStatus(GameStatus.FINISHED);
        gameRoomRepository.save(room);
        
        return winningNumber;
    }

    @Override
    public void startNewGame(int gameRoomId) {
        if (!BuilderUtils.ISCLIENT) {
            return;
        }

        Optional<GameRoom> roomOpt = gameRoomRepository.findById(gameRoomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }
        
        GameRoom room = roomOpt.get();
        room.getBids().clear();
        room.setStatus(GameStatus.BETTING);
        gameRoomRepository.save(room);
    }

    @Override
    public int findGameRoomId(EntityPlayerMP player) {
        if (!BuilderUtils.ISCLIENT) {
            return -1;
        }

        return Integration.integration.getCurrentGameRoomId(player.getName());
    }

    @Override
    public List<PlayerBetDTO> findBetsByGameRoomId(int gameRoomId) {
        if (!BuilderUtils.ISCLIENT) {
            return new ArrayList<>();
        }

        Optional<GameRoom> roomOpt = gameRoomRepository.findById(gameRoomId);
        if (!roomOpt.isPresent()) {
            return Collections.emptyList();
        }

        GameRoom room = roomOpt.get();
        return room.getBids().stream()
                .map(bid -> BetMapper.toDTO(bid, room.getId()))
                .collect(Collectors.toList());
    }
    
    private boolean isWinningBid(Bet bid, int winningNumber, SectionColor winningColor) {
        if (!BuilderUtils.ISCLIENT) {
            return false;
        }

        SectionColor section = bid.getSection();
        
        // Check number match
//        if (section.getNumber() != -1 && section.getNumber() == winningNumber) {
//            return true;
//        }
        
        // Check color match
        return section == winningColor;
    }
    
    private void payoutWinner(Bet bid) {
        if (!BuilderUtils.ISCLIENT) {
            return;
        }

        if (bid.isWinner()) {
            return;
        }

        Integer balance = balanceRepository.findBalance(bid.getPlayer())
                .orElseThrow(() -> new IllegalArgumentException("Player balance not found"));

        Integer payout = calculatePayout(bid);
        balanceRepository.saveBalance(bid.getPlayer(), balance + payout);
    }
    
    private Integer calculatePayout(Bet bid) {
        if (!BuilderUtils.ISCLIENT) {
            return 0;
        }

        SectionColor section = bid.getSection();
        
//        // Straight number bet pays 35:1
//        if (section.getNumber() != -1) {
//            return bid.getAmount() * 35;
//        }
//
//        // Color bet pays 1:1
//        if (section.getColor() != null) {
//            return bid.getAmount() * 2; // original + winnings
//        }
        
        return section == null ? 0 : (section == SectionColor.GREEN ? bid.getAmount() * 17 : bid.getAmount() * 2);
    }
    
    private SectionColor getColorForNumber(int number) {
        if (number == 0) return SectionColor.GREEN;
        
        // European roulette red numbers
        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        return Arrays.stream(redNumbers).anyMatch(n -> n == number) ? SectionColor.RED : SectionColor.BLACK;
    }
}