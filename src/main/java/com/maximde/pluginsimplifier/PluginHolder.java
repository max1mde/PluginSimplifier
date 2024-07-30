package com.maximde.pluginsimplifier;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginHolder {
    @Getter
    @Setter
    private static JavaPlugin pluginInstance;

}