package com.maximde.pluginsimplifier.command;

import java.util.List;

/**
 * Functional interface for handling tab completion logic.
 */
@FunctionalInterface
public interface SimplifierComplete {
    /**
     * Handles tab completion logic.
     *
     * @param context The context of the command.
     * @return A list of completions.
     */
    List<String> complete(CommandContext context);
}