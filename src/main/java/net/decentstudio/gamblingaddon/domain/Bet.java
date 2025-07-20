package net.decentstudio.gamblingaddon.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class Bet {
    private final UUID id;
    private final String nick;
    private final EntityPlayerMP player;
    private final SectionColor section;
    private final Integer amount;
    private boolean isWinner = false; // looks like an unused field if i dont make a bet story
}