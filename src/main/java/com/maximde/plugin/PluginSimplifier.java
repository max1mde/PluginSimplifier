package com.maximde.plugin;

import com.maximde.pluginsimplifier.Command.CommandRegistry;
import com.maximde.pluginsimplifier.Events.EventsRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginSimplifier extends JavaPlugin {

    @Override
    public void onEnable() {
        onStart();
        CommandRegistry.registerCommands();
        EventsRegistry.registerEvents();
    }

    @Override
    public void onDisable() {
        onStop();
    }

    protected abstract void onStart();

    protected abstract void onStop();
}