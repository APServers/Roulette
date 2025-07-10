package net.decentstudio.gamblingaddon.integration;

import net.decentstudio.gamblingaddon.integration.bukkit.BukkitIntegration;

public class Integration {

    public static final IIntegration integration;

    static {
        IIntegration local;
        try {
            Class.forName("org.bukkit.Bukkit");
            local = new BukkitIntegration();
        } catch (Exception e) {
            local = new EmptyIntegration();
        }

        integration = local;
    }
}
