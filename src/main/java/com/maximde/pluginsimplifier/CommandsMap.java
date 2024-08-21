package com.maximde.pluginsimplifier;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

/**
 * Provides access to the server's command map.
 */
public class CommandsMap {
    private static CommandMap commandMap;

    static {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (final Exception e) {
            PluginHolder.get().getLogger().severe("Could not retrieve command map: " + e.getMessage());
        }
    }

    /**
     * Gets the command map.
     *
     * @return The command map used by the server.
     */
    public static CommandMap get() {
        return commandMap;
    }
}