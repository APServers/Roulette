package net.decentstudio.gamblingaddon.repository.impl;

import net.decentstudio.gamblingaddon.domain.Bet;
import net.decentstudio.gamblingaddon.repository.BidRepository;

import java.util.*;

public class BidRepositoryImpl implements BidRepository {
    private final Map<UUID, Bet> bids = new HashMap<>();
    
    @Override
    public void save(Bet bid) {
        bids.put(bid.getId(), bid);
    }
    
    @Override
    public Optional<Bet> findById(UUID id) {
        return Optional.ofNullable(bids.get(id));
    }
    
    @Override
    public List<Bet> findByGameRoomId(int gameRoomId) {
        // This would need gameRoomId in Bid class or different approach
        return new ArrayList<>(bids.values());
    }
}