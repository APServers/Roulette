package net.decentstudio.gamblingaddon.repository.impl;

import net.decentstudio.gamblingaddon.domain.Gambler;
import net.decentstudio.gamblingaddon.repository.GamblerRepository;

import java.util.*;

public class GamblerRepositoryImpl implements GamblerRepository {
    private final Map<UUID, Gambler> gamblers = new HashMap<>();
    
    @Override
    public void save(Gambler gambler) {
        gamblers.put(gambler.getId(), gambler);
    }
    
    @Override
    public Optional<Gambler> findById(UUID id) {
        return Optional.ofNullable(gamblers.get(id));
    }
    
    @Override
    public List<Gambler> findAll() {
        return new ArrayList<>(gamblers.values());
    }
}