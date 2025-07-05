package net.decentstudio.gamblingaddon.repository;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

public interface BalanceRepository {
    Optional<Integer> findBalance(EntityPlayer player);
    void saveBalance(EntityPlayer player, int balance);
}
