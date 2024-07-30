package com.maximde.pluginsimplifier.command;

import com.maximde.pluginsimplifier.PluginHolder;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;

public class CommandRegistrar {
    private CommandMap commandMap;

    public CommandRegistrar() {
        JavaPlugin plugin = PluginHolder.getPluginInstance();
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            this.commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not retrieve command map", e);
        }
    }

    public void registerCommand(@NonNull String name, @NonNull CommandExecutor executor, @NonNull String description, @NonNull String aliases, @NonNull String permission) {
        JavaPlugin plugin = PluginHolder.getPluginInstance();
        try {
            Command command = new BukkitCommand(name) {
                @Override
                public boolean execute(@NonNull CommandSender sender, @NonNull String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };

            command.setDescription(description);
            command.setAliases(List.of(aliases.split(",")));
            command.setPermission(permission);

            commandMap.register(plugin.getPluginMeta().getName(), command);
        } catch (final Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not register command: " + name, e);
        }
    }
}