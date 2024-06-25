package com.maximde.pluginsimplifier;

import com.maximde.pluginsimplifier.commands.CommandRegistry;
import com.maximde.pluginsimplifier.events.EventsRegistry;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginSimplifier extends JavaPlugin {

    @Getter
    private static PluginSimplifier pluginInstance;

    @Override
    public void onEnable() {
        pluginInstance = this;

        CommandRegistry.registerCommands();
        EventsRegistry.registerEvents();
    }

    @Override
    public void onDisable() {

    }


}