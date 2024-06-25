package com.maximde.plugin;

public class PluginHolder {
    private static PluginSimplifier pluginInstance;

    public static void setPluginInstance(PluginSimplifier plugin) {
        pluginInstance = plugin;
    }

    public static PluginSimplifier getPluginInstance() {
        return pluginInstance;
    }
}