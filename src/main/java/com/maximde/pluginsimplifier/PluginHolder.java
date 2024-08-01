package com.maximde.pluginsimplifier;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginHolder {
    private static JavaPlugin pluginInstance;

    public static JavaPlugin getPluginInstance() {
        return pluginInstance;
    }

    public static void setPluginInstance(JavaPlugin pluginInstance) {
        PluginHolder.pluginInstance = pluginInstance;
    }
}