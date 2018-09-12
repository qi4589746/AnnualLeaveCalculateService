package com.mycena.utils.calculator.service;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.UUID;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public final class IdGenerator {
    public static String generateId(Class<?> aClass) {
        Config idGenerate = aClass.getDeclaredAnnotation(Config.class);
        if (idGenerate == null) {
            throw new IllegalArgumentException("No IdGenerator.Config annotation in " + aClass);
        }
        return idGenerate.prefix() + "-" + UUID.randomUUID().toString();
    }

    @Target(TYPE)
    @Retention(RUNTIME)
    public @interface Config {
        String prefix();
    }
}