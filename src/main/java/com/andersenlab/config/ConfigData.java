package com.andersenlab.config;

public class ConfigData {

    private DatabaseConfig database;

    private ApartmentConfig apartment;

    private SaveOptionConfig saveOption;

    private PostgresDB postgresDatabase;

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

    public SaveOptionConfig getSaveOption() {
        return saveOption;
    }

    public void setSaveOption(SaveOptionConfig saveOption) {
        this.saveOption = saveOption;
    }

    public PostgresDB getPostgresDatabase() {
        return postgresDatabase;
    }

    public void setPostgresDatabase(PostgresDB postgresDatabase) {
        this.postgresDatabase = postgresDatabase;
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

        private boolean allowApartmentStatusChange;

        public ApartmentConfig() {
        }

        public ApartmentConfig(boolean allowApartmentStatusChange) {
            this.allowApartmentStatusChange = allowApartmentStatusChange;
        }

        public boolean isAllowApartmentStatusChange() {
            return allowApartmentStatusChange;
        }

        public void setAllowApartmentStatusChange(boolean allowApartmentStatusChange) {
            this.allowApartmentStatusChange = allowApartmentStatusChange;
        }
    }

    public static class SaveOptionConfig {

        private boolean saveOnDisk;
        private boolean saveJdbc;

        public SaveOptionConfig() {
        }

        public boolean isSaveOnDisk() {
            return saveOnDisk;
        }

        public boolean isSaveJdbc() {
            return saveJdbc;
        }

        public void setSaveOnDisk(boolean saveOnDisk) {
            this.saveOnDisk = saveOnDisk;
        }

        public void setSaveJdbc(boolean saveJdbc) {
            this.saveJdbc = saveJdbc;
        }
    }

    public static class PostgresDB {
        private String url;
        private String username;
        private String password;

        private int pool;

        public PostgresDB() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getPool() {
            return pool;
        }

        public void setPool(int pool) {
            this.pool = pool;
        }
    }
}