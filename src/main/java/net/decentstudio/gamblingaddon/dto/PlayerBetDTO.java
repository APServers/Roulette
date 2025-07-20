package net.decentstudio.gamblingaddon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.decentstudio.gamblingaddon.util.game.SectionColor;

@Getter
@Setter
@AllArgsConstructor
public class PlayerBetDTO {

    private String nick;
    private int chips;
    private SectionColor sectionColor;
    private int roomId;
}
