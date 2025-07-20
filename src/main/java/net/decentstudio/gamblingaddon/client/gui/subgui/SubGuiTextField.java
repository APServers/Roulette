package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.narutocraft.client.gui.subgui.SubGuiImage;
import com.narutocraft.client.texture.api.ITextures;
import lombok.Getter;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.decentstudio.gamblingaddon.util.ui.UIConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SubGuiTextField extends SubGuiImage {

    protected boolean isEdit;
    protected List<Character> charsAllowed = new ArrayList<>();
    protected boolean changed;
    protected int color;

    @Getter
    protected String text;

    public SubGuiTextField(GuiRoulette parent,
                           int id,
                           float posX,
                           float posY,
                           float u,
                           float v,
                           int uWidth,
                           int vHeight,
                           int width,
                           int height,
                           float tileWidth,
                           float tileHeight,
                           ITextures texture,
                           String defaultValue,
                           char[] charsAllowed) {
        this(parent, id,
                posX, posY,
                u, v,
                uWidth, vHeight,
                width, height,
                tileWidth, tileHeight,
                texture, defaultValue,
                Color.BLACK.getRGB(), charsAllowed);
    }

    public SubGuiTextField(GuiRoulette parent,
                           int id,
                           float posX,
                           float posY,
                           float u,
                           float v,
                           int uWidth,
                           int vHeight,
                           int width,
                           int height,
                           float tileWidth,
                           float tileHeight,
                           ITextures texture,
                           String defaultValue,
                           int color,
                           char[] charsAllowed) {

        super(parent, id, posX, posY, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight, texture);
        this.isEdit = false;
        this.text = defaultValue;
        this.changed = false;
        this.color = color;

        for(char c : charsAllowed) {
            this.charsAllowed.add(c);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        if (predicate != null && !predicate.getAsBoolean()) return;
        GlStateManager.pushMatrix();
        texture.bind();
        GL11.glTranslated(posX + pressedTime * 0.4 + currentOffsetX, posY + pressedTime * 0.2 + currentOffsetY, zLevel);
        GL11.glScaled(1 - pressedTime * 0.01, 1 - pressedTime * 0.01, 1);
        drawScaledCustomSizeModalRect(0, 0, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glPushMatrix();

        String text = this.text;
        if(UIConstants.FONT.width(text) > width - 4) {
            text = ".." + Minecraft.getMinecraft().fontRenderer.trimStringToWidth(text, width - 4, true);
        }
        if(isEdit) {
            text += "_";
        }

        GL11.glTranslatef(2, 1.5F / ((GuiRoulette) parent).scale, 1);
        GL11.glScaled(3 / ((GuiRoulette) parent).scale, 3 / ((GuiRoulette) parent).scale, 3 / ((GuiRoulette) parent).scale);

        UIConstants.FONT.drawString(text,
                0,
                0,
                color);

        GL11.glPopMatrix();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.popMatrix();

        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
            if (hoverConsumer != null) {
                hoverConsumer.accept(this);
            }
        } else {
            if (noHoverConsumer != null) {
                noHoverConsumer.accept(this);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(isEdit) {
            if (keyCode == 14) {
                if (!text.isEmpty()) {
                    text = text.substring(0, text.length() - 1);
                    if (parent instanceof ITextFieldListener) {
                        ((ITextFieldListener) parent).textFieldChanged(this);
                        changed = true;
                    }
                }
            } else {
                if (text.length() < 10) {
                    try {
                        if (charsAllowed == null || charsAllowed.isEmpty()) {
                            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                                text += typedChar;
                                if (parent instanceof ITextFieldListener) {
                                    ((ITextFieldListener) parent).textFieldChanged(this);
                                    changed = true;
                                }
                            }
                        } else {
                            if (charsAllowed.contains(typedChar)) {
                                text += typedChar;
                                if (parent instanceof ITextFieldListener) {
                                    ((ITextFieldListener) parent).textFieldChanged(this);
                                    changed = true;
                                }
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean tryClick(int mouseX, int mouseY, int mouseButton) {
        if(changed) {
            if(parent instanceof ITextFieldListener) {
                ((ITextFieldListener) parent).endTextFieldChanged(this);
            }
            changed = false;
        }
        this.isEdit = false;
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
            isEdit = true;
        }

        return super.tryClick(mouseX, mouseY, mouseButton);
    }
}