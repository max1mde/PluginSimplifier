package com.maximde.pluginsimplifier.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Register {
    String name();

    String description() default "";

    String aliases() default "";

    String permission() default "";
}