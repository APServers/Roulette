package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.narutocraft.client.gui.subgui.SubGuiImage;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.decentstudio.gamblingaddon.util.ui.GuiTextures;

public class SubGuiBlock extends SubGuiImage {

    private final int inputId, maxId, doubleId, halfId, resetId;

    public SubGuiBlock(GuiRoulette parent, int id,
                       float posX, float posY,
                       int inputId, int maxId, int doubleId, int halfId, int resetId) {

        super(parent, id,
                posX, posY,
                0, 0,
                838, 679,
                Math.round(838 / parent.scale), Math.round(679 / parent.scale),
                838, 679,
                GuiTextures.BLOCK);

        this.inputId = inputId;
        this.maxId = maxId;
        this.doubleId = doubleId;
        this.halfId = halfId;
        this.resetId = resetId;
    }

    @Override
    public void initGui() {
        float xDistance = 9 / ((GuiRoulette) parent).scale;

        SubGuiTextField input = new SubGuiTextField((GuiRoulette) parent, inputId,
                posX + 25 / ((GuiRoulette) parent).scale,
                posY + 65 / ((GuiRoulette) parent).scale,
                0, 0,
                321, 43,
                Math.round(321 / ((GuiRoulette) parent).scale), Math.round(43 / ((GuiRoulette) parent).scale),
                321, 43,
                GuiTextures.INPUT,
                "Type an amount..");

        ((GuiRoulette) parent).addImage(input);

        SubGuiAmountButton x2 = new SubGuiAmountButton((GuiRoulette) parent, doubleId,
                input.posX + input.width + xDistance,
                input.posY,
                "x2");

        ((GuiRoulette) parent).addButton(x2);

        SubGuiAmountButton half = new SubGuiAmountButton((GuiRoulette) parent, halfId,
                x2.posX + x2.width + xDistance,
                input.posY,
                "1/2");

        ((GuiRoulette) parent).addButton(half);

        SubGuiAmountButton max = new SubGuiAmountButton((GuiRoulette) parent, maxId,
                half.posX + half.width + xDistance,
                input.posY,
                "Max.");

        ((GuiRoulette) parent).addButton(max);

        SubGuiAmountButton reset = new SubGuiAmountButton((GuiRoulette) parent, resetId,
                max.posX + max.width + xDistance,
                input.posY,
                "Reset");

        ((GuiRoulette) parent).addButton(reset);

        xDistance = 45 / ((GuiRoulette) parent).scale;
        SubGuiBetBlock redBetBlock = new SubGuiBetBlock((GuiRoulette) parent,
                input.posX,
                input.posY + input.height + 108 / ((GuiRoulette) parent).scale,
                SectionColor.RED);

        ((GuiRoulette) parent).addImage(redBetBlock);
        redBetBlock.initGui();

        SubGuiBetBlock greenBetBlock = new SubGuiBetBlock((GuiRoulette) parent,
                redBetBlock.posX + redBetBlock.width + xDistance,
                redBetBlock.posY,
                SectionColor.GREEN);

        ((GuiRoulette) parent).addImage(greenBetBlock);
        greenBetBlock.initGui();

        SubGuiBetBlock blackBetBlock = new SubGuiBetBlock((GuiRoulette) parent,
                greenBetBlock.posX + greenBetBlock.width + xDistance,
                redBetBlock.posY,
                SectionColor.BLACK);

        ((GuiRoulette) parent).addImage(blackBetBlock);
        blackBetBlock.initGui();
    }
}
