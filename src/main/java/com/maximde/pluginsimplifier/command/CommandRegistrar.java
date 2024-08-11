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

    public static CommandBuilder command(String name, CommandExecutor executor) {
        return new CommandBuilder(name, executor);
    }

    public static class CommandBuilder {
        private final String name;
        private final CommandExecutor executor;
        private String namespace;
        private String usage;
        private String description;
        private List<String> aliases;
        private String permission;
        private TabCompleter completer;

        private CommandBuilder(String name, CommandExecutor executor) {
            this.name = name;
            this.executor = executor;
        }

        public CommandBuilder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public CommandBuilder usage(String usage) {
            this.usage = usage;
            return this;
        }

        public CommandBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CommandBuilder aliases(List<String> aliases) {
            this.aliases = aliases;
            return this;
        }

        public CommandBuilder permission(String permission) {
            this.permission = permission;
            return this;
        }

        public CommandBuilder completer(TabCompleter completer) {
            this.completer = completer;
            return this;
        }

        public void register() {
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
}