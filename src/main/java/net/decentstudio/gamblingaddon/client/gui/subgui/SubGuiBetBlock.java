package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.google.common.collect.Lists;
import com.narutocraft.client.gui.subgui.SubGuiImage;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.util.ui.UIConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.decentstudio.gamblingaddon.util.ui.GuiTextures;

import java.awt.*;
import java.util.List;

public class SubGuiBetBlock extends SubGuiImage {

    private final List<String> players = Lists.newArrayList("Bob", "Ashley", "Den", "John", "Jane", "Stacy", "Rob", "Michael", "Tom", "Jerry");

    private final int blockElementsAmount = 9;
    private final SectionColor sectionColor;
    private final int fullScrollHeight;
    private final int scrollHeight;

    public int scrollOffset;
    public boolean isScrolling;
    public int startScrollingY;

    public SubGuiBetBlock(GuiRoulette parent,
                          float posX, float posY,
                          SectionColor sectionColor) {

        super(parent, sectionColor.startIdFrom(),
                Math.round(posX), Math.round(posY),
                0, 0,
                225, 415,
                Math.round(225 / parent.scale), Math.round(415 / parent.scale),
                225, 415,
                GuiTextures.BET_BOX);

        this.sectionColor = sectionColor;
        this.fullScrollHeight = Math.round(358 / parent.scale);
        int contentHeight = players.size() * fullScrollHeight / blockElementsAmount;
//        int contentHeight = Math.round((38 + 2) / parent.scale);
        scrollHeight = Math.min(fullScrollHeight, (int) (fullScrollHeight * (float)fullScrollHeight / contentHeight));
        scrollOffset = 0;

        System.out.println("SCALE " + new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor());
    }

    @Override
    public void initGui() {
        int index = sectionColor.startIdFrom() + 1;
        SubGuiBetButton bet = new SubGuiBetButton((GuiRoulette) parent, index++,
                posX,
                posY - 54F / ((GuiRoulette) parent).scale,
                sectionColor);

        ((GuiRoulette) parent).addButton(bet);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated(0, -1, -10);
        texture.bind();
        drawScaledCustomSizeModalRect((int) posX, (int) posY,
                0, 0,
                this.uWidth, this.vHeight,
                Math.round(this.width + 5 / ((GuiRoulette) parent).scale), Math.round(this.height + 5 / ((GuiRoulette) parent).scale),
                this.tileWidth, this.tileHeight);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(0, 0, -3);
        drawScaledCustomSizeModalRect((int) posX, (int) posY,
                0, 10,
                1, 1,
                this.width + Math.round(5 / ((GuiRoulette) parent).scale), Math.round(56 / ((GuiRoulette) parent).scale),
                225, 40);
        GL11.glPopMatrix();

        drawScroll();
        handleScroll(mouseX, mouseY);
        drawBets();
    }

    private void drawBets() {
        GlStateManager.pushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glTranslatef(this.posX, (this.posY + 57 / ((GuiRoulette) parent).scale) - scrollOffset, -5);

        int posY = 0;
        float textX = 8 / ((GuiRoulette) parent).scale;
        float textY;

        for (String player : players) {
            GuiTextures.SELECT_BOX.bind();
            drawScaledCustomSizeModalRect(0, posY,
                    0, 0,
                    225, 40,
                    this.width + Math.round(3 / ((GuiRoulette) parent).scale), fullScrollHeight / blockElementsAmount,
                    225, 40);

            GL11.glPushMatrix();
            textY = posY + 1.4F / ((GuiRoulette) parent).scale;

            GL11.glTranslatef(textX, textY, 1);
            GL11.glScaled(3 / ((GuiRoulette) parent).scale, 3 / ((GuiRoulette) parent).scale, 3 / ((GuiRoulette) parent).scale);

            UIConstants.FONT.drawString(player,
                    0,
                    0,
                    Color.WHITE.getRGB());

            GL11.glPopMatrix();

            posY += fullScrollHeight / blockElementsAmount;
        }

        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.popMatrix();
    }

    private void drawScroll() {
        int x = Math.round(this.posX + this.width + 7 / ((GuiRoulette) parent).scale);
        int y = Math.round(this.posY + 57 / ((GuiRoulette) parent).scale);
        int scrollWidth = Math.round(15 / ((GuiRoulette) parent).scale);
        int scrollBiggestPartHeight = Math.round(7 / ((GuiRoulette) parent).scale);

        GuiTextures.SCROLL_EMPTY.bind();
        drawScaledCustomSizeModalRect(x, y, 0, 0, 15, 7, scrollWidth, scrollBiggestPartHeight, 15, 15);
        drawScaledCustomSizeModalRect(x, y + scrollBiggestPartHeight, 0, 7, 15, 1, scrollWidth, fullScrollHeight - scrollBiggestPartHeight * 2, 15, 15);
        drawScaledCustomSizeModalRect(x, y + fullScrollHeight - scrollBiggestPartHeight, 0, 8, 15, 7, scrollWidth, scrollBiggestPartHeight, 15, 15);

        GuiTextures.SCROLL.bind();
        drawScaledCustomSizeModalRect(x, y + scrollOffset, 0, 0, 15, 7, scrollWidth, scrollBiggestPartHeight, 15, 15);
        drawScaledCustomSizeModalRect(x, y + scrollBiggestPartHeight + scrollOffset, 0, 7, 15, 1, scrollWidth, scrollHeight - scrollBiggestPartHeight * 2, 15, 15);
        drawScaledCustomSizeModalRect(x, y + scrollHeight - scrollBiggestPartHeight + scrollOffset, 0, 8, 15, 7, scrollWidth, scrollBiggestPartHeight, 15, 15);
    }

    private void handleScroll(int mouseX, int mouseY) {
        int x = Math.round(7 / ((GuiRoulette) parent).scale + Math.round(15 / ((GuiRoulette) parent).scale));
        if (mouseX < posX || mouseX > posX + width + x || mouseY < posY || mouseY > posY + height) {
            return;
        }

        if (isScrolling) {
            if (!Mouse.isButtonDown(0)) isScrolling = false;
            else {
                if (scrollOffset - (startScrollingY - mouseY) > fullScrollHeight + 3) {
                    scrollOffset = fullScrollHeight;
                } else if(scrollOffset - (startScrollingY - mouseY) < 0) {
                    scrollOffset = 0;
                } else {
                    if (-(startScrollingY - mouseY) != 0) {
                        scrollOffset -= (startScrollingY - mouseY);
                        startScrollingY = mouseY;
                    }
                }
            }
        }
        int wheel = Mouse.getDWheel() / 15;
        if(scrollOffset - wheel < 0) {
            scrollOffset = 0;
        }
        else if(scrollOffset - wheel > fullScrollHeight - scrollHeight) {
            scrollOffset = fullScrollHeight - scrollHeight;
        }
        else scrollOffset -= wheel;
    }
}
