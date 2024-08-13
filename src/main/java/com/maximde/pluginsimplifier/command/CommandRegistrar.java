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

    /**
     * Creates a new {@link CommandBuilder} instance for defining a command.
     *
     * <p>This method checks that neither the command name nor the executor is null,
     * throwing an exception if either is found to be so. After checking, it returns a
     * new {@code CommandBuilder} instance configured with the provided name and executor.
     *
     * @param name     The name of the command to be registered.
     * @param executor The {@link CommandExecutor} responsible for executing the command logic.
     * @return A {@link CommandBuilder} instance for further configuration of the command.
     * @throws NullPointerException if either the command name or executor is null.
     */
    public static CommandBuilder command(String name, CommandExecutor executor) {
        if (name == null) throw new NullPointerException("Command name cannot be null");
        if (executor == null) throw new NullPointerException("Command executor cannot be null");
        return new CommandBuilder(name, executor);
    }

    /**
     * The CommandBuilder class allows for the configuration and registration of a command.
     */
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

        /**
         * Sets the namespace for the command. Default uses plugin's name as the namespace
         *
         * @param namespace The namespace to set.
         * @return This CommandBuilder instance for method chaining.
         */
        public CommandBuilder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        /**
         * Sets the usage message for the command.
         *
         * @param usage The usage message to set.
         * @return This CommandBuilder instance for method chaining.
         */
        public CommandBuilder usage(String usage) {
            this.usage = usage;
            return this;
        }

        /**
         * Sets the description for the command.
         *
         * @param description The description to set.
         * @return This CommandBuilder instance for method chaining.
         */
        public CommandBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the aliases for the command.
         *
         * @param aliases The list of aliases to set.
         * @return This CommandBuilder instance for method chaining.
         */
        public CommandBuilder aliases(List<String> aliases) {
            this.aliases = aliases;
            return this;
        }

        /**
         * Sets the permission required to execute the command.
         *
         * @param permission The permission to set.
         * @return This CommandBuilder instance for method chaining.
         */
        public CommandBuilder permission(String permission) {
            this.permission = permission;
            return this;
        }

        /**
         * Sets the TabCompleter for the command, which handles tab completion logic.
         *
         * @param completer The TabCompleter to set.
         * @return This CommandBuilder instance for method chaining.
         */
        public CommandBuilder completer(TabCompleter completer) {
            this.completer = completer;
            return this;
        }

        /**
         * Registers the command itself. This method must be called to make the command available.
         */
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