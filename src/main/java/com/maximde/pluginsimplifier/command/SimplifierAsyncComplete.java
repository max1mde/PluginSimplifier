package com.maximde.pluginsimplifier.command;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Functional interface for handling asynchronous tab completion logic.
 */
@FunctionalInterface
public interface SimplifierAsyncComplete {
    /**
     * Handles tab completion logic asynchronously.
     *
     * @param context The context of the command.
     * @return A CompletableFuture with a list of completions.
     */
    CompletableFuture<List<String>> asyncComplete(CommandContext context);
}