package com.andersenlab.util;

import com.andersenlab.config.ConfigData;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHandler {

    public static ConfigData createConfig(String[] args) {
        String path = "runtime/resources/config-dev.yaml";
        ConfigData config;
        if (args.length == 1) {
            path = args[0];
        }

        System.out.println("Usage: " + path);


        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(Path.of(path))) {
            config = yaml.loadAs(in, ConfigData.class);
            System.out.println(config.toString());
        } catch (IOException e) {
            throw new RuntimeException("yaml problems", e);
        }
        return config;
    }
}