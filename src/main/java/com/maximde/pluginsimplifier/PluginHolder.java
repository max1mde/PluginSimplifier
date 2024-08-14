package com.maximde.pluginsimplifier;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Holds an instance of {@link JavaPlugin}
 */
public class PluginHolder {
    private static JavaPlugin pluginInstance = null;

    /**
     * Retrieves the currently held instance of {@link JavaPlugin}.
     *
     * @return The current instance of {@link JavaPlugin}, never null after being set.
     * @throws NullPointerException If this method is called before setting an instance via {@link #setPluginInstance(JavaPlugin)}.
     * @see #setPluginInstance(JavaPlugin)
     */
    public static JavaPlugin getPluginInstance() {
        if (pluginInstance == null) {
            throw new NullPointerException("Something tried to get the plugin instance while its not set");
        }
        return pluginInstance;
    }

    /**
     * Sets the instance of {@link JavaPlugin} to be held by this class.
     *
     * @param pluginInstance The instance of {@link JavaPlugin} to hold.
     * @throws NullPointerException If the pluginInstance is null
     * @see #getPluginInstance()
     */
    public static void setPluginInstance(JavaPlugin pluginInstance) {
        if (pluginInstance == null) {
            throw new NullPointerException("Something tried to set a null plugin instance");
        }
        PluginHolder.pluginInstance = pluginInstance;
    }
}