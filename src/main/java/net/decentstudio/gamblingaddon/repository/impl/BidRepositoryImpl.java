package net.decentstudio.gamblingaddon.repository.impl;

import net.decentstudio.gamblingaddon.domain.Bid;
import net.decentstudio.gamblingaddon.repository.BidRepository;

import java.util.*;

public class BidRepositoryImpl implements BidRepository {
    private final Map<UUID, Bid> bids = new HashMap<>();
    
    @Override
    public void save(Bid bid) {
        bids.put(bid.getId(), bid);
    }
    
    @Override
    public Optional<Bid> findById(UUID id) {
        return Optional.ofNullable(bids.get(id));
    }
    
    @Override
    public List<Bid> findByGameRoomId(int gameRoomId) {
        // This would need gameRoomId in Bid class or different approach
        return new ArrayList<>(bids.values());
    }
}