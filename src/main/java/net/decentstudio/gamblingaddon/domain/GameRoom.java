package net.decentstudio.gamblingaddon.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.decentstudio.gamblingaddon.util.game.GameStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GameRoom {
    private final int id;
    private final List<Bet> bids = new ArrayList<>();
    private GameStatus status = GameStatus.WAITING;
}