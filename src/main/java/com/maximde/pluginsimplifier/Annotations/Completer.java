package com.maximde.pluginsimplifier.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Completer {
    Class<? extends org.bukkit.command.TabCompleter> value();
}