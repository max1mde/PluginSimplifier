package com.maximde.pluginsimplifier;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Holds the plugin instance for easy access throughout the plugin.
 */
@SuppressWarnings("unused")
public class PluginHolder {
    private static JavaPlugin pluginInstance = null;

    /**
     * Gets the plugin instance.
     *
     * @return The current plugin instance.
     * @throws NullPointerException if the plugin instance has not been set.
     */
    public static JavaPlugin get() {
        if (pluginInstance == null) throw new NullPointerException("Plugin instance not set");
        return pluginInstance;
    }

    /**
     * Sets the plugin instance.
     *
     * @param pluginInstance The plugin instance to set.
     * @throws NullPointerException if the provided plugin instance is null.
     */
    public static void set(JavaPlugin pluginInstance) {
        if (pluginInstance == null) throw new NullPointerException("Cannot set a null plugin instance");
        PluginHolder.pluginInstance = pluginInstance;
    }
}