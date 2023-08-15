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

        public SaveOptionConfig() {
        }

        public boolean isSaveOnDisk() {
            return saveOnDisk;
        }

        public void setSaveOnDisk(boolean saveOnDisk) {
            this.saveOnDisk = saveOnDisk;
        }
    }

    public static class PostgresDB {
       private String POSTGRES_USER;
       private String POSTGRES_PASS;
       private String DB_URL;

        public PostgresDB() {
        }

        public String getPOSTGRES_USER() {
            return POSTGRES_USER;
        }

        public void setPOSTGRES_USER(String POSTGRES_USER) {
            this.POSTGRES_USER = POSTGRES_USER;
        }

        public String getPOSTGRES_PASS() {
            return POSTGRES_PASS;
        }

        public void setPOSTGRES_PASS(String POSTGRES_PASS) {
            this.POSTGRES_PASS = POSTGRES_PASS;
        }

        public String getDB_URL() {
            return DB_URL;
        }

        public void setDB_URL(String DB_URL) {
            this.DB_URL = DB_URL;
        }
    }
}