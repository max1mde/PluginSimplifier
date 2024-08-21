package com.maximde.pluginsimplifier.command;

import com.maximde.pluginsimplifier.CommandsMap;
import com.maximde.pluginsimplifier.PluginHolder;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A builder class for creating and configuring commands with various options.
 * <p>
 * Use this class to build a command with a name, executor, description, usage, aliases,
 * permissions, and tab completion handlers.
 * </p>
 */
@SuppressWarnings("unused")
public class CommandBuilder {

    private final String name;
    private SimplifierCommand executor;
    private String description;
    private String usage;
    private List<String> aliases = new ArrayList<>();
    private String permission;
    private String namespace;
    private boolean asyncTabComplete;
    private SimplifierComplete completeHandler;
    private SimplifierAsyncComplete asyncCompleteHandler;
    private final List<String> flags = new ArrayList<>();

    /**
     * Private constructor to initialize the CommandBuilder with a command name.
     *
     * @param name The name of the command.
     */
    private CommandBuilder(String name) {
        this.name = name;
    }

    /**
     * Creates a new CommandBuilder instance for a command with the specified name.
     *
     * @param name The name of the command.
     * @return A new CommandBuilder instance.
     */
    public static CommandBuilder create(String name) {
        return new CommandBuilder(name);
    }

    /**
     * Sets the executor for the command.
     *
     * @param executor The command executor.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder executor(SimplifierCommand executor) {
        this.executor = executor;
        return this;
    }

    /**
     * Sets the description for the command.
     *
     * @param description The description of the command.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the usage string for the command.
     *
     * @param usage The usage string of the command.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder usage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Sets the aliases for the command.
     *
     * @param aliases The aliases of the command.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder aliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
        return this;
    }

    /**
     * Sets the aliases for the command.
     *
     * @param aliases The list of aliases for the command.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder aliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    /**
     * Sets the namespace for the command.
     *
     * @param namespace The namespace to register the command under.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * Sets the permission required to execute the command.
     *
     * @param permission The permission string.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder permission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Sets an asynchronous tab completion handler for the command.
     *
     * @param handler The handler for asynchronous tab completion.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder asyncComplete(SimplifierAsyncComplete handler) {
        this.asyncTabComplete = true;
        this.asyncCompleteHandler = handler;
        return this;
    }

    /**
     * Sets a synchronous tab completion handler for the command.
     *
     * @param handler The handler for synchronous tab completion.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder complete(SimplifierComplete handler) {
        this.completeHandler = handler;
        return this;
    }

    /**
     * Adds a flag to the command.
     *
     * @param flag The flag to add.
     * @return The CommandBuilder instance for chaining.
     */
    public CommandBuilder flag(String flag) {
        this.flags.add(flag);
        return this;
    }

    /**
     * Registers the command with the server using the configured options.
     */
    public void register() {
        CustomCommand command = new CustomCommand(name, executor, description, usage, aliases, permission, asyncTabComplete, completeHandler, asyncCompleteHandler);
        CommandsMap.get().register(namespace == null ? PluginHolder.get().getName() : namespace, command);
    }

    /**
     * A custom command class that extends BukkitCommand to handle command execution and tab completion.
     */
    private static class CustomCommand extends BukkitCommand {
        private final SimplifierCommand executor;
        private final boolean asyncTabComplete;
        private final SimplifierComplete completeHandler;
        private final SimplifierAsyncComplete asyncCompleteHandler;

        /**
         * Constructs a new CustomCommand with the specified parameters.
         *
         * @param name                 The name of the command.
         * @param executor             The command executor.
         * @param description          The description of the command.
         * @param usage                The usage string of the command.
         * @param aliases              The list of aliases for the command.
         * @param permission           The permission required to execute the command.
         * @param asyncTabComplete     Whether the command supports asynchronous tab completion.
         * @param completeHandler      The synchronous tab completion handler.
         * @param asyncCompleteHandler The asynchronous tab completion handler.
         */
        public CustomCommand(String name, SimplifierCommand executor, String description, String usage, List<String> aliases, String permission, boolean asyncTabComplete, SimplifierComplete completeHandler, SimplifierAsyncComplete asyncCompleteHandler) {
            super(name);
            this.executor = executor;
            this.asyncTabComplete = asyncTabComplete;
            this.completeHandler = completeHandler;
            this.asyncCompleteHandler = asyncCompleteHandler;
            if (description != null) setDescription(description);
            if (usage != null) setUsage(usage);
            if (aliases != null) setAliases(aliases);
            if (permission != null) setPermission(permission);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            try {
                executor.execute(new CommandContext(sender, label, args));
                return true;
            } catch (CommandExecutionException e) {
                sender.sendMessage(e.getMessage());
                return false;
            }
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
            CommandContext context = new CommandContext(sender, alias, args);
            try {
                if (asyncTabComplete) {
                    return Objects.requireNonNullElseGet(asyncCompleteHandler.asyncComplete(context).join(), List::of);
                } else if (completeHandler != null) {
                    return Objects.requireNonNullElseGet(completeHandler.complete(context), List::of);
                }
            } catch (final Exception e) {
                return List.of();
            }
            return List.of();
        }
    }
}