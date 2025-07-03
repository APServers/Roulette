package net.decentstudio.gamblingaddon.client.gui.subgui;

import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.util.ui.GuiTextures;

public class SubGuiAmountButton extends SubGuiTextButton {

    public SubGuiAmountButton(GuiRoulette parent, int id,
                              float posX, float posY,
                              String buttonText) {

        super(parent, id,
                posX, posY,
                0, 0,
                102, 43,
                Math.round(102 / parent.scale), Math.round(43 / parent.scale),
                102, 43,
                GuiTextures.BUTTON,
                buttonText);
    }
}
