package com.andersenlab.config;

public class ConfigData {

    private DatabaseConfig database;

    public ConfigData() {
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public static class DatabaseConfig {
        private String path;

        public DatabaseConfig() {
        }

        public DatabaseConfig(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
        
    }
}