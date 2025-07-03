package net.decentstudio.gamblingaddon.client.gui;

import com.narutocraft.api.client.gui.ISubGuiItem;
import com.narutocraft.client.font.CustomFontsEnum;
import com.narutocraft.client.gui.NarutoCraftGuiScreen;
import com.narutocraft.client.gui.subgui.SubGuiButton;
import net.decentstudio.gamblingaddon.client.gui.subgui.*;
import net.decentstudio.gamblingaddon.util.ui.GuiIds;
import net.decentstudio.gamblingaddon.util.ui.GuiTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiRoulette extends NarutoCraftGuiScreen implements ITextFieldListener {

    // TODO EXTRACT ALL STRINGS TO I18N AND IDS TO CONSTANTS

    public float scale;

    private Language language;

    public GuiRoulette() {
    }

    public void initGui() {
        scale = 1650F / (width - 100F);

//        language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
        subGuiItemList.clear();

        SubGuiImageTransient background = new SubGuiImageTransient(this, GuiIds.BACKGROUND,
                width / 2F - 1650 / scale / 2f, height / 2F - 850 / scale / 2f,
                0, 0,
                1650, 850,
                Math.round(1650 / scale), Math.round(850 / scale),
                1650, 850,
                GuiTextures.BACKGROUND);

        addImage(background);

        SubGuiWheel wheel = new SubGuiWheel(this, GuiIds.WHEEL,
                background.posX + 54 / scale, background.posY + 119 / scale,
                GuiIds.ARROW);

        addImage(wheel);
        wheel.initGui();

        SubGuiBlock block = new SubGuiBlock(this, GuiIds.BLOCK,
                background.posX + 776 / scale, background.posY + 116 / scale,
                GuiIds.INPUT, GuiIds.MAX, GuiIds.DOUBLE, GuiIds.HALF, GuiIds.RESET);

        addImage(block);
        block.initGui();

        SubGuiBalance balance = new SubGuiBalance(this, 10,
                background.posX + background.width - 243 / scale - 110 / scale, background.posY + 23 / scale,
                0, 0,
                243, 70,
                Math.round(243 / scale), Math.round(70 / scale),
                243, 70,
                GuiTextures.BALANCE_BLOCK);

        addImage(balance);

        SubGuiButton closeButton = new SubGuiButton(this, 11,
                background.posX + background.width - 70 / scale, background.posY + 45 / scale,
                0, 0,
                48, 48,
                Math.round(24 / scale), Math.round(24 / scale),
                48, 48,
                GuiTextures.CLOSE_BUTTON);

        addButton(closeButton);
    }

    @Override
    public void itemClick(ISubGuiItem iSubGuiItem, int i) {
        if (iSubGuiItem instanceof SubGuiButton) {
            SubGuiButton button = (SubGuiButton) iSubGuiItem;
            button.startPressAnimation();

            if (button.getId() == 11) {
                mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        int startX = width / 2 - 320 / 2;
        if (mouseX <= startX + 70 || mouseX >= startX + 200) {
            return;
        }

        double dx = mouseX - (width / 2f);
        double dy = mouseY - (height / 2f);
        double angle = Math.atan2(dy, dx);
//        rotate = (float) (Math.toDegrees(angle) - 90);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

//        drawTitle();
    }

    private void drawTitle() {
        GlStateManager.pushMatrix();
        String text = I18n.format("text.challenges");
        GL11.glTranslated(width / 2 - CustomFontsEnum.MINION14.font.width(text) / 2 - 26,
                height / 2 - 93,
                0);

        GL11.glScaled(2, 2, 1);

        CustomFontsEnum.MINION14.font.drawString(text,
                0,
                0,
                Color.WHITE.getRGB());

        GlStateManager.popMatrix();

//        GlStateManager.pushMatrix();
//        GlStateManager.disableCull();
//        GlStateManager.disableBlend();
//
//        GL11.glTranslated(width / 2f - 20, height / 2f + 69, 100f);
//        GlStateManager.scale(-55f, -55f, 55f);
//        GL11.glRotatef(180 + rotate, 0, 1, 0);
//
//        Minecraft.getMinecraft().renderEngine.bindTexture(getSelectedOpponent().getSkin() == null ?
//                new ResourceLocation("minecraft", "textures/entity/steve.png") :
//                getSelectedOpponent().getSkin());
//
//        AdvancedModelLoader.loadModel(new ResourceLocation(ChallengesAddon.MODID, "geo/steve.obj"))
//                .renderAll();
//
//        GlStateManager.popMatrix();
    }


    private boolean isPointInRegion(double x, int y, int width, int height, int pointX, int pointY) {
        return pointX >= x && pointX < x + width && pointY >= y && pointY < y + height;
    }

    private void scaleTextBasedOnLanguage() {
        if (language.getLanguageCode().startsWith("ru_") || language.getLanguageCode().startsWith("uk_")) {
            GL11.glScaled(0.8, 0.8, 1);
        } else {
            GL11.glTranslated(0, 1.2, 0);
            GL11.glScaled(0.6, 0.6, 1);
        }
    }

    @Override
    public void textFieldChanged(SubGuiTextField textField) {

    }

    @Override
    public void endTextFieldChanged(SubGuiTextField textField) {

    }
}
