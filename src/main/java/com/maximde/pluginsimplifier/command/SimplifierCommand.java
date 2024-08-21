package com.maximde.pluginsimplifier.command;

/**
 * Interface for creating commands with execution logic.
 */
@SuppressWarnings("unused")
public interface SimplifierCommand {

    /**
     * Executes the command logic.
     *
     * @param context The context containing all information about the command execution.
     */
    void execute(CommandContext context);
}