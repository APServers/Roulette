package net.decentstudio.gamblingaddon.repository;

import net.decentstudio.gamblingaddon.domain.Gambler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GamblerRepository {
    void save(Gambler gambler);
    Optional<Gambler> findById(UUID id);
    List<Gambler> findAll();
}
