package edu.aitu.oop3.patterns;

public class GymConfig {
    private static GymConfig instance;
    private String dbUrl;
    private double taxRate;
    private String adminPassword;

    private GymConfig() {
        // Загрузка настроек из переменных окружения или значения по умолчанию
        this.dbUrl = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/postgres");
        this.taxRate = Double.parseDouble(System.getenv().getOrDefault("TAX_RATE", "0.12"));
        this.adminPassword = System.getenv().getOrDefault("ADMIN_PASSWORD", "admin");
    }

    public static synchronized GymConfig getInstance() {
        if (instance == null) {
            instance = new GymConfig();
        }
        return instance;
    }

    public String getDbUrl() { return dbUrl; }
    public double getTaxRate() { return taxRate; }
    public String getAdminPassword() { return adminPassword; }
}
