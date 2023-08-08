package com.andersenlab.config;

public enum Config {
    INSTANCE;

    ConfigData configData;

    public ConfigData getConfigData() {
        return configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }
}