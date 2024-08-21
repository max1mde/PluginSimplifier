package com.maximde.pluginsimplifier.command;

/**
 * Exception thrown when there is an error during command execution.
 */
public class CommandExecutionException extends RuntimeException {
    /**
     * Creates a new CommandExecutionException with the specified message.
     *
     * @param message The error message.
     */
    public CommandExecutionException(String message) {
        super(message);
    }
}