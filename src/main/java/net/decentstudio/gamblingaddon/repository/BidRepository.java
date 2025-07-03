package net.decentstudio.gamblingaddon.repository;

import net.decentstudio.gamblingaddon.domain.Bid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BidRepository {
    void save(Bid bid);
    Optional<Bid> findById(UUID id);
    List<Bid> findByGameRoomId(int gameRoomId);
}