package net.decentstudio.gamblingaddon.util.ui.font;

import net.decentstudio.gamblingaddon.GamblingAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.Font;

public class FontContainer {

        private TrueTypeFont textFont = null;
        
        public boolean useCustomFont = true;
        
        private FontContainer() {}
        
        public FontContainer(String fontType, int fontSize) {
          this.textFont = new TrueTypeFont(new Font(fontType, 0, fontSize), 1.0F);
          this.useCustomFont = !fontType.equalsIgnoreCase("minecraft");
          try {
              System.out.println("[Fonts] Checking font directory..");
              if (!this.useCustomFont || fontType.isEmpty() || fontType.equalsIgnoreCase("default")) {
                  this.textFont = new TrueTypeFont(new ResourceLocation(GamblingAddon.MODID, "Roboto.ttf"), fontSize, 1.0F);
              }
          } catch (Exception e) {
              System.out.println("[Fonts] A font cannot be loaded");
              System.out.println(e.toString());
          }
        }
        
        public int height(String text) {
          if (this.useCustomFont)
            return this.textFont.height(text);
          return (Minecraft.getMinecraft()).fontRenderer.FONT_HEIGHT;
        }
        
        public int width(String text) {
          if (this.useCustomFont)
            return this.textFont.width(text);
          return (Minecraft.getMinecraft()).fontRenderer.getStringWidth(text);
        }
        
        public FontContainer copy() {
          FontContainer font = new FontContainer();
          font.textFont = this.textFont;
          font.useCustomFont = this.useCustomFont;
          return font;
        }
        
        public void drawString(String text, float x, float y, int color) {
          if (this.useCustomFont) {
            this.textFont.draw(text, x, y, color);
          } else {
            (Minecraft.getMinecraft()).fontRenderer.drawString(text, Math.round(x), Math.round(y), color);
          }
        }
        
        public String getName() {
          if (!this.useCustomFont)
            return "Minecraft";
          return this.textFont.getFontName();
        }
        
        public void clear() {
          if (this.textFont != null)
            this.textFont.dispose();
        }
}