package net.decentstudio.gamblingaddon.repository.impl;

import com.narutocraft.NarutoCraft;
import com.narutocraft.ninjaplayer.data.service.INinjaPlayerSession;
import net.decentstudio.gamblingaddon.repository.BalanceRepository;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

public class BalanceRepositoryImpl implements BalanceRepository {
    @Override
    public Optional<Integer> findBalance(EntityPlayer player) {
        INinjaPlayerSession ninjaPlayer = NarutoCraft.getSession(player).orElse(null);
        if (ninjaPlayer == null) {
            return Optional.empty();
        }

        return Optional.of(ninjaPlayer.getCasinoPoints());
    }

    @Override
    public void saveBalance(EntityPlayer player, int balance) {
        INinjaPlayerSession ninjaPlayer = NarutoCraft.getSession(player).orElse(null);
        if (ninjaPlayer != null) {
            ninjaPlayer.setCasinoPoints(balance);
        }
    }
}
