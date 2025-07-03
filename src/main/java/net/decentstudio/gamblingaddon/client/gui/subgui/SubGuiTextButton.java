package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.narutocraft.client.gui.subgui.SubGuiButton;
import com.narutocraft.client.texture.api.ITextures;
import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.util.ui.UIConstants;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class SubGuiTextButton extends SubGuiButton {

    private final String buttonText;

    public SubGuiTextButton(GuiRoulette parent, int id,
                            float posX, float posY,
                            float u, float v,
                            int uWidth, int vHeight,
                            int width, int height,
                            float tileWidth, float tileHeight,
                            ITextures texture,
                            String buttonText) {

        super(parent, id, posX, posY, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight, texture);
        this.buttonText = buttonText;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            if (this.predicate == null || this.predicate.getAsBoolean()) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();

                this.texture.bind();
                GL11.glTranslated((double) this.posX + (double) this.pressedTime * 0.4 + (double) this.currentOffsetX, (double) this.posY + (double) this.pressedTime * 0.2 + (double) this.currentOffsetY, (double) this.zLevel);
                GL11.glScaled(1.0 - (double) this.pressedTime * 0.01, 1.0 - (double) this.pressedTime * 0.01, 1.0);

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                drawScaledCustomSizeModalRect(0, 0, this.u, this.v, this.uWidth, this.vHeight, this.width, this.height, this.tileWidth, this.tileHeight);

                GlStateManager.pushMatrix();

                GL11.glScaled(3 / ((GuiRoulette) parent).scale, 3 / ((GuiRoulette) parent).scale, 3 / ((GuiRoulette) parent).scale);
                float textX = 4;
                float textY = (height - 41 / ((GuiRoulette) parent).scale);

                UIConstants.FONT.drawString(buttonText,
                        textX,
                        textY,
                        Color.WHITE.getRGB());

                GlStateManager.popMatrix();

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableBlend();

                if ((float) mouseX >= this.posX && (float) mouseX <= this.posX + (float) this.width && (float) mouseY >= this.posY && (float) mouseY <= this.posY + (float) this.height) {
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
