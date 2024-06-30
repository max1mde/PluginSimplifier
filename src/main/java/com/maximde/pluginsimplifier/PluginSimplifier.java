package com.maximde.pluginsimplifier;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginSimplifier extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            getClass().getDeclaredMethod("onStart");
            onStart();
        } catch (NoSuchMethodException ignored) {

        }
    }

    @Override
    public void onDisable() {
        try {
            getClass().getDeclaredMethod("onStop");
            onStop();
        } catch (NoSuchMethodException ignored) {

        }
    }

    /**
     * Optional method to be overridden by subclasses.
     * Called automatically during plugin startup after initialization.
     */
    protected void onStart() {

    }

    /**
     * Optional method to be overridden by subclasses.
     * Called automatically during plugin shutdown.
     */
    protected void onStop() {

    }
}