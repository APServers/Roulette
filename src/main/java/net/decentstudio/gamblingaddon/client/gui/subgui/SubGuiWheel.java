package net.decentstudio.gamblingaddon.client.gui.subgui;

import com.narutocraft.client.gui.subgui.SubGuiImage;
import net.decentstudio.gamblingaddon.domain.BidSection;
import net.decentstudio.gamblingaddon.client.gui.GuiRoulette;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.decentstudio.gamblingaddon.util.game.SectionColor;
import net.decentstudio.gamblingaddon.util.ui.GuiTextures;

import java.util.Arrays;

public class SubGuiWheel extends SubGuiImage {

    private static final int[] SECTIONS_NUMBERS = new int[] { 0, 1, 8, 2, 9, 3, 10, 4, 11, 5, 12, 6, 13, 7, 14 };
    public static BidSection[] SECTIONS = new BidSection[SECTIONS_NUMBERS.length];

    private final float scale;
    private final int arrowId;

    private final float sectionAngle = 360.0F / SECTIONS_NUMBERS.length;
    //    private final int section = 0;
    private final int section = (int) (Math.random() * SECTIONS_NUMBERS.length);
    private final float startFrom = (188F + getAngleBySection(SECTIONS[section])) % 360;

    private float angle = 0.0F;
    private float speed = 5;
    private boolean passed = false;

    static {
        boolean red = true;
        for (int i = 0; i < SECTIONS_NUMBERS.length; i++) {
            SectionColor color = i == 0 ? SectionColor.GREEN : (red ? SectionColor.RED : SectionColor.BLACK);
            SECTIONS[i] = new BidSection(color, SECTIONS_NUMBERS[i]);
            if (color != SectionColor.GREEN) {
                red = !red;
            }
        }
    }

    public SubGuiWheel(GuiRoulette parent,
                       int id,
                       float posX,
                       float posY,
                       int arrowId) {

        super(parent, id,
                posX, posY,
                0, 0,
                676, 676,
                Math.round(676 / parent.scale), Math.round(676 / parent.scale),
                676, 676,
                GuiTextures.WHEEL);

        this.scale = parent.scale;
        this.arrowId = arrowId;
        //Minecraft.getMinecraft().player.sendChatMessage("section: " + SECTIONS[section].getNumber() + " startFrom: " + startFrom);
    }

    @Override
    public void initGui() {
        SubGuiImage arrow = new SubGuiImage(parent, arrowId,
                posX + width / 2F - (71F / scale / 2F), posY - 10 / scale,
                0, 0,
                71, 45,
                Math.round(71 / scale), Math.round(45 / scale),
                71, 45,
                GuiTextures.ARROW);

        ((GuiRoulette)parent).addImage(arrow);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            if (this.predicate == null || this.predicate.getAsBoolean()) {
                GlStateManager.pushMatrix();

                GlStateManager.enableBlend();

                this.texture.bind();
                GL11.glTranslated((double)this.posX + (double)this.pressedTime * 0.4 + (double)this.currentOffsetX, (double)this.posY + (double)this.pressedTime * 0.2 + (double)this.currentOffsetY, (double)this.zLevel);
                GL11.glScaled((1.0 - (double)this.pressedTime * 0.01),
                        (1.0 - (double)this.pressedTime * 0.01),
                        1.0);

                GL11.glTranslated((double) width / 2, (double) height / 2, 0);
                GL11.glRotatef(-angle, 0, 0, 1);
                GL11.glTranslated(-((double) width / 2), -((double) height / 2), 0);

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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

    @Override
    public void tick() {
        super.tick();

        boolean off = true;
        if (off) {
            return;
        }
        if (speed > 0) {
            angle += 1 * speed;
            if (passed) {
                speed -= 0.01;
            } else if (angle >= startFrom) {
                passed = true;
            }

            angle = angle % 360.0F;

            if (speed <= 0) {
                BidSection section = getSectionByAngle(angle);
                Minecraft.getMinecraft().player.sendChatMessage("Section Number: " + section.getNumber() + " " + section.getColor().name() + " Angle: " + angle);
            }
        }

        //scale = (float) (Math.sin(Math.toRadians(angle)) * 0.3 + 0.7);
    }

    private BidSection getSectionByAngle(float angle) {
        int section = (int) ((angle + sectionAngle / 2) / 360 * SECTIONS_NUMBERS.length);
//        section = Math.floorMod(section, SECTIONS_NUMBERS.length);
//        Minecraft.getMinecraft().player.sendChatMessage("Section id: " + section + " Angle: " + angle + " WITHOUT ROUNDING " + ((angle - sectionAngle / 2) / 360 * SECTIONS_NUMBERS.length));
        return SECTIONS[section];
    }

    private float getAngleBySection(BidSection section) {
        return (float) (Arrays.asList(SECTIONS).indexOf(section) * 360) / SECTIONS_NUMBERS.length - sectionAngle + sectionAngle / 2;
    }
}
