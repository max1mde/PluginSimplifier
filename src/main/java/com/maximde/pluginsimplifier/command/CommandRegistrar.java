package com.maximde.pluginsimplifier.command;

import com.maximde.pluginsimplifier.PluginHolder;
import com.maximde.pluginsimplifier.PluginSimplifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class CommandRegistrar {
    private CommandMap commandMap;

    public CommandRegistrar() {
        PluginSimplifier plugin = PluginHolder.getPluginInstance();
        try {
            Field commandMapField = getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            this.commandMap = (CommandMap) commandMapField.get(getServer());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not retrieve command map", e);
        }
    }

    public void registerCommand(@NotNull String name, @NotNull CommandExecutor executor, @NotNull String description, @NotNull String aliases, @NotNull String permission) {
        PluginSimplifier plugin = PluginHolder.getPluginInstance();
        try {
            Command command = new BukkitCommand(name) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };

            command.setDescription(description);
            command.setAliases(List.of(aliases.split(",")));
            command.setPermission(permission);

            commandMap.register(plugin.getDescription().getName(), command);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not register command: " + name, e);
        }
    }
}