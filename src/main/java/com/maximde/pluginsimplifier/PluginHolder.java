package com.maximde.pluginsimplifier;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginHolder {
    private static JavaPlugin pluginInstance = null;

    public static JavaPlugin getPluginInstance() {
        if (pluginInstance == null) {
            throw new RuntimeException("Something tried to get the plugin instance while its not set");
        }
        return pluginInstance;
    }

    public static void setPluginInstance(JavaPlugin pluginInstance) {
        PluginHolder.pluginInstance = pluginInstance;
    }
}