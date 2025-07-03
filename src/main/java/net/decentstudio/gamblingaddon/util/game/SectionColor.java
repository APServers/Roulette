package net.decentstudio.gamblingaddon.util.game;

import com.narutocraft.client.texture.ResourceTexture;
import lombok.Getter;
import net.decentstudio.gamblingaddon.util.ui.GuiTextures;

@Getter
public enum SectionColor {

    RED(GuiTextures.BUTTON_RED),
    BLACK(GuiTextures.BUTTON_BLACK),
    GREEN(GuiTextures.BUTTON_GREEN);

    private final ResourceTexture buttonTexture;

    SectionColor(ResourceTexture buttonTexture) {
        this.buttonTexture = buttonTexture;
    }

    public int startIdFrom() {
        return (ordinal() + 2) * 100;
    }
}
