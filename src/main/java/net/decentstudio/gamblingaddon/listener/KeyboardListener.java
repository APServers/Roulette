package net.decentstudio.gamblingaddon.listener;

import net.decentstudio.gamblingaddon.GamblingAddon;
import net.decentstudio.gamblingaddon.network.S1PacketOpenRouletteGui;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = GamblingAddon.MODID, value = Side.CLIENT)
public class KeyboardListener {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_F9)) {
            System.out.println("F9 pressed");
            GamblingAddon.NETWORK.sendToServer(new S1PacketOpenRouletteGui());
        }
    }
}
