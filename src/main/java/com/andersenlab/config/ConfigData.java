package com.andersenlab.config;

public class ConfigData {

    private DatabaseConfig database;

    private ApartmentConfig apartment;

    public ConfigData() {
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public ApartmentConfig getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentConfig apartment) {
        this.apartment = apartment;
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

    public static class ApartmentConfig {

        private boolean mutableStatus;

        public ApartmentConfig() {
        }

        public ApartmentConfig(boolean mutableStatus) {
            this.mutableStatus = mutableStatus;
        }

        public boolean isMutableStatus() {
            return mutableStatus;
        }

        public void setMutableStatus(boolean mutableStatus) {
            this.mutableStatus = mutableStatus;
        }
    }
}