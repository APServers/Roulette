package net.decentstudio.gamblingaddon.repository;

import net.decentstudio.gamblingaddon.domain.GameRoom;

import java.util.List;
import java.util.Optional;

public interface GameRoomRepository {
    void save(GameRoom gameRoom);
    Optional<GameRoom> findById(int id);
    List<GameRoom> findAll();
}