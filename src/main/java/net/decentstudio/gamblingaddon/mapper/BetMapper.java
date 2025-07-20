package net.decentstudio.gamblingaddon.mapper;

import net.decentstudio.gamblingaddon.domain.Bet;
import net.decentstudio.gamblingaddon.dto.PlayerBetDTO;

public class BetMapper {

    public static PlayerBetDTO toDTO(Bet bet, int roomId) {
        if (bet == null) {
            return null;
        }

        return new PlayerBetDTO(bet.getNick(), bet.getAmount(), bet.getSection(), roomId);
    }
}
