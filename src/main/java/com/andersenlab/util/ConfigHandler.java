package com.andersenlab.util;

import com.andersenlab.config.ConfigData;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

public class ConfigHandler {
    private static final String DEFAULT_PATH = "src/main/resources/config/config-dev.yaml";


    public static ConfigData createConfig(String possiblePath) {
        String actualPath = Optional
                .ofNullable(possiblePath)
                .filter(Predicate.not(String::isEmpty))
                .orElse(DEFAULT_PATH);

        try (InputStream in = Files.newInputStream(Path.of(actualPath))) {
            return new Yaml().loadAs(in, ConfigData.class);
        } catch (IOException e) {
            throw new RuntimeException("Yaml problems", e);
        }
    }
}