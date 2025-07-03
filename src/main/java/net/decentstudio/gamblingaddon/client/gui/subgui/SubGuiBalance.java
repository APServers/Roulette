package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.narutocraft.client.gui.subgui.SubGuiImage;
import com.narutocraft.client.texture.api.ITextures;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import org.lwjgl.opengl.GL11;
import net.decentstudio.gamblingaddon.util.ui.GuiTextures;

public class SubGuiBalance extends SubGuiImage {

    public SubGuiBalance(GuiRoulette parent, int id,
                         float posX, float posY,
                         float u, float v,
                         int uWidth, int vHeight,
                         int width, int height,
                         float tileWidth, float tileHeight,
                         ITextures texture) {

        super(parent, id, posX, posY, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight, texture);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GL11.glPushMatrix();

        this.texture.bind();
        GL11.glTranslatef(posX, posY, 0);
        drawScaledCustomSizeModalRect(0, 0,
                0, 0,
                this.uWidth, this.vHeight,
                this.width, this.height,
                this.tileWidth, this.tileHeight);

        GL11.glPopMatrix();

        GL11.glPushMatrix();

        GuiTextures.CHIPS.bind();
        GL11.glTranslatef(posX + 15 / ((GuiRoulette) parent).scale, posY + 15 / ((GuiRoulette) parent).scale, 1);
        drawScaledCustomSizeModalRect(0, 0,
                0, 0,
                200, 200,
                Math.round(40 / ((GuiRoulette) parent).scale), Math.round(40 / ((GuiRoulette) parent).scale),
                200, 200);

        GL11.glPopMatrix();
    }
}
