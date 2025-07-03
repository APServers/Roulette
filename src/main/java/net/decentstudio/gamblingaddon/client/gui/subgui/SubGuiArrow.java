package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.narutocraft.client.gui.NarutoCraftGuiScreen;
import com.narutocraft.client.gui.subgui.SubGuiButton;
import com.narutocraft.client.texture.api.ITextures;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class SubGuiArrow extends SubGuiButton {

    private final boolean up, available;

    public SubGuiArrow(NarutoCraftGuiScreen parent,
                       int id,
                       float posX, float posY,
                       float u, float v,
                       int uWidth, int vHeight,
                       int width, int height,
                       float tileWidth, float tileHeight,
                       ITextures texture,
                       boolean up, boolean available) {

        super(parent, id, posX, posY, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight, texture);
        this.up = up;
        this.available = available;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            if (this.predicate == null || this.predicate.getAsBoolean()) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();

                this.texture.bind();

                GL11.glTranslated((double)this.posX + (double)this.pressedTime * 0.4 + (double)this.currentOffsetX, (double)this.posY + (double)this.pressedTime * 0.2 + (double)this.currentOffsetY, (double)this.zLevel);
                if (!up) {
                    GL11.glTranslated(this.width, this.height, 0);
                    GL11.glRotatef(180, 0, 0, 1);
                }
                GL11.glScaled(1.0 - (double)this.pressedTime * 0.01, 1.0 - (double)this.pressedTime * 0.01, 1.0);

                GlStateManager.color(1.0F, 1.0F, 1.0F, available ? 1.0F : 0.3F);
                drawScaledCustomSizeModalRect(0, 0, this.u, this.v, this.uWidth, this.vHeight, this.width, this.height, this.tileWidth, this.tileHeight);

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
