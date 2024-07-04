package com.maximde.pluginsimplifier;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginSimplifier extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            getClass().getDeclaredMethod("onPluginStart");
            onPluginStart();
        } catch (NoSuchMethodException ignored) {

        }
    }

    @Override
    public void onLoad() {
        try {
            getClass().getDeclaredMethod("onPluginLoad");
            onPluginLoad();
        } catch (NoSuchMethodException ignored) {

        }
    }

    @Override
    public void onDisable() {
        try {
            getClass().getDeclaredMethod("onPluginStop");
            onPluginStop();
        } catch (NoSuchMethodException ignored) {

        }
    }

    protected void onPluginStart() {

    }

    protected void onPluginLoad() {

    }

    protected void onPluginStop() {

    }
}