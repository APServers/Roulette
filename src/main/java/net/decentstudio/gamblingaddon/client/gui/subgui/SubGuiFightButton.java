package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.narutocraft.client.font.CustomFontsEnum;
import com.narutocraft.client.gui.NarutoCraftGuiScreen;
import com.narutocraft.client.gui.subgui.SubGuiButton;
import com.narutocraft.client.texture.api.ITextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class SubGuiFightButton extends SubGuiButton {

    public SubGuiFightButton(NarutoCraftGuiScreen parent, int id, float posX, float posY, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, ITextures texture) {
        super(parent, id, posX, posY, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight, texture);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            if (this.predicate == null || this.predicate.getAsBoolean()) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();

                this.texture.bind();
                GL11.glTranslated((double)this.posX + (double)this.pressedTime * 0.4 + (double)this.currentOffsetX, (double)this.posY + (double)this.pressedTime * 0.2 + (double)this.currentOffsetY, (double)this.zLevel);
                GL11.glScaled(1.0 - (double)this.pressedTime * 0.01, 1.0 - (double)this.pressedTime * 0.01, 1.0);

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                drawScaledCustomSizeModalRect(0, 0, this.u, this.v, this.uWidth, this.vHeight, this.width, this.height, this.tileWidth, this.tileHeight);

                GlStateManager.pushMatrix();
                //GL11.glTranslated(-83, -42, 0);

                String buttonText = I18n.format("text.fight-button");

                GL11.glScaled(2, 2, 2);
                CustomFontsEnum.MINION14.font.drawString(buttonText,
                        width / 2 - CustomFontsEnum.MINION14.font.width(buttonText) / 2 - 20,
                        height - 31,
                        Color.WHITE.getRGB());

                GlStateManager.popMatrix();

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableBlend();

                if ((float)mouseX >= this.posX && (float)mouseX <= this.posX + (float)this.width && (float)mouseY >= this.posY && (float)mouseY <= this.posY + (float)this.height) {
                    if (this.hoverConsumer != null) {
                        this.hoverConsumer.accept(this);
                    }
                } else if (this.noHoverConsumer != null) {
                    this.noHoverConsumer.accept(this);
                }

                GlStateManager.popMatrix();
            }
        }
    }
}
