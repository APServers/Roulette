package net.decentstudio.gamblingaddon.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.decentstudio.gamblingaddon.util.game.SectionColor;

@RequiredArgsConstructor
@Getter
@Setter
public class BidSection {

    private final SectionColor color;
    private final int number;
}
