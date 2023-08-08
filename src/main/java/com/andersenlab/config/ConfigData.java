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
        private DatabaseConfig.CustomPath path;

        public DatabaseConfig() {
        }

        public DatabaseConfig(DatabaseConfig.CustomPath path) {
            this.path = path;
        }

        public CustomPath getPath() {
            return path;
        }

        public void setPath(CustomPath path) {
            this.path = path;
        }

        public record CustomPath(String path) {
        }
    }
}