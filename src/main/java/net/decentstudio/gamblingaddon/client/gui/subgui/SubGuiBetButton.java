package net.decentstudio.gamblingaddon.client.gui.subgui;

import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.util.game.SectionColor;

public class SubGuiBetButton extends SubGuiTextButton {

    public SubGuiBetButton(GuiRoulette parent, int id,
                           float posX, float posY,
                           SectionColor sectionColor) {

        super(parent, id,
                posX, posY,
                0, 0,
                225, 43,
                Math.round(225 / parent.scale), Math.round(43 / parent.scale),
                225, 43,
                sectionColor.getButtonTexture(),
                "Bet");
    }
}
