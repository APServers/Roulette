package net.decentstudio.gamblingaddon.integration.bukkit;

import net.decentstudio.gamblingaddon.integration.IIntegration;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.UUID;

public class BukkitIntegration implements IIntegration {

    @Override
    public int getCurrentGameRoomId(String nick) {
        return BukkitUtils.getCurrentGameRoomId(nick);
    }
}