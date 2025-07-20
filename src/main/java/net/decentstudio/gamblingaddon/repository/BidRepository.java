package net.decentstudio.gamblingaddon.repository;

import net.decentstudio.gamblingaddon.domain.Bet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BidRepository {
    void save(Bet bid);
    Optional<Bet> findById(UUID id);
    List<Bet> findByGameRoomId(int gameRoomId);
}