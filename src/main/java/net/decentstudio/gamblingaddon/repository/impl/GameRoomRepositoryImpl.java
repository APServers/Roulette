package net.decentstudio.gamblingaddon.repository.impl;

import net.decentstudio.gamblingaddon.domain.GameRoom;
import net.decentstudio.gamblingaddon.repository.GameRoomRepository;

import java.util.*;

public class GameRoomRepositoryImpl implements GameRoomRepository {
    private final Map<Integer, GameRoom> gameRooms = new HashMap<>();
    
    @Override
    public void save(GameRoom gameRoom) {
        gameRooms.put(gameRoom.getId(), gameRoom);
    }
    
    @Override
    public Optional<GameRoom> findById(int id) {
        return Optional.ofNullable(gameRooms.get(id));
    }
    
    @Override
    public List<GameRoom> findAll() {
        return new ArrayList<>(gameRooms.values());
    }
}