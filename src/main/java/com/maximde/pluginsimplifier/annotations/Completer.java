package com.maximde.pluginsimplifier.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Completer {
    Class<? extends org.bukkit.command.TabCompleter> value();
}