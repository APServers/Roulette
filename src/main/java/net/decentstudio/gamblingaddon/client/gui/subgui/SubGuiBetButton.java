package net.decentstudio.gamblingaddon.client.gui.subgui;

import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.decentstudio.gamblingaddon.util.ui.GuiIds;

public class SubGuiBetButton extends SubGuiTextButton {

    private final SectionColor sectionColor;

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

        this.sectionColor = sectionColor;
    }

    public void handleClick() {
        GuiRoulette gui;
        if (!(parent instanceof GuiRoulette)) {
            return;
        }

        gui = (GuiRoulette) parent;
        SubGuiTextField input;
        if (parent.getItem(GuiIds.INPUT) instanceof SubGuiTextField) {
            input = (SubGuiTextField) parent.getItem(GuiIds.INPUT);
            if (input.getText().isEmpty()) {
                return;
            }
        } else {
            return;
        }

        int chips = 0;
        try {
            chips = Integer.parseInt(input.getText());
        } catch (NumberFormatException e) {
            return;
        }

        if (chips <= 0 || chips > gui.getBalance()) {
            // todo some error message
            return;
        }

        GamblingAddon.proxy.bet(gui.mc.player, chips, sectionColor);
    }
}
