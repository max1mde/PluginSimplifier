package com.maximde.pluginsimplifier.command;

import com.maximde.pluginsimplifier.PluginHolder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;

public class CommandRegistrar {
    private static CommandMap commandMap;

    static {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (final Exception e) {
            PluginHolder.getPluginInstance().getLogger().log(Level.SEVERE, "Could not retrieve command map", e);
        }
    }

    public static void register(String name, CommandExecutor executor) {
        register(name, executor, null, null, null, null, null, null);
    }

    public static void register(String name, CommandExecutor executor, String namespace) {
        register(name, executor, namespace, null, null, null, null, null);
    }

    public static void register(String name, CommandExecutor executor, String namespace, String usage) {
        register(name, executor, namespace, usage, null, null, null, null);
    }

    public static void register(String name, CommandExecutor executor, String namespace, String usage, String description) {
        register(name, executor, namespace, usage, description, null, null, null);
    }

    public static void register(String name, CommandExecutor executor, String namespace, String usage, String description, List<String> aliases) {
        register(name, executor, namespace, usage, description, aliases, null, null);
    }

    public static void register(String name, CommandExecutor executor, String namespace, String usage, String description, List<String> aliases, String permission) {
        register(name, executor, namespace, usage, description, aliases, permission, null);
    }

    public static void register(String name, CommandExecutor executor, String namespace, String usage, String description, List<String> aliases, String permission, TabCompleter completer) {
        JavaPlugin plugin = PluginHolder.getPluginInstance();
        CustomCommand command = new CustomCommand(name, executor, completer);
        if (description != null) {
            command.setDescription(description);
        }
        if (aliases != null) {
            command.setAliases(aliases);
        }
        if (permission != null) {
            command.setPermission(permission);
        }
        if (usage != null) {
            command.setUsage(usage.replaceAll("<command>", name));
        }
        commandMap.register(namespace != null ? namespace : plugin.getName(), command);
    }
}