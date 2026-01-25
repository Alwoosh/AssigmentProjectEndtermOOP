package edu.aitu.oop3.patterns;

public class GymConfig {
    private static GymConfig instance;
    private String dbUrl;
    private double taxRate;
    private String adminPassword;

    private GymConfig() {
        // Симуляция загрузки настроек
        this.dbUrl = "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
        this.taxRate = 0.12;
        this.adminPassword = "Medina";
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
