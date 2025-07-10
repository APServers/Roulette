package net.decentstudio.gamblingaddon.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayer;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class Bid {
    private final UUID id;
    private final EntityPlayer player;
    private final BidSection section;
    private final Integer amount;
    private boolean isWinner = false; // looks like an unused field if i dont make a bet story
}