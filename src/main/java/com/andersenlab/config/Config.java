package com.andersenlab.config;

import com.andersenlab.util.ConfigHandler;
import org.springframework.stereotype.Component;


public class Config {

    public Config(String path) {
        configData = ConfigHandler.createConfig(path);
    }

    private ConfigData configData;

    public ConfigData getConfigData() {
        return configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }
}


