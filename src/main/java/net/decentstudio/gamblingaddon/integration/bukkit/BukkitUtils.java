package net.decentstudio.gamblingaddon.integration.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BukkitUtils {

    private static final Method GET_CURRENT_GAME_ROOM_ID;

    static {
        Method GET_CURRENT_GAME_ROOM_ID_METHOD = null;
        try {
            Object pluginManager = Class.forName("org.bukkit.Bukkit")
                    .getDeclaredMethod("getPluginManager")
                    .invoke(null);

            Object plugin = pluginManager.getClass()
                    .getMethod("getPlugin", String.class)
                    .invoke(pluginManager, "NcMain");

            Class<?> pluginIntegration = plugin.getClass()
                    .getClassLoader()
                    .loadClass("com.narutocraftplugin.integration.UtilForge");

            GET_CURRENT_GAME_ROOM_ID_METHOD = pluginIntegration.getMethod("getCurrentCasinoRoomId", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GET_CURRENT_GAME_ROOM_ID = GET_CURRENT_GAME_ROOM_ID_METHOD;
    }

    public static int getCurrentGameRoomId(String nick) {
        try {
            return (int) GET_CURRENT_GAME_ROOM_ID.invoke(null, nick);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
