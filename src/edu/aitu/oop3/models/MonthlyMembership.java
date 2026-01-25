package edu.aitu.oop3.models;

public class MonthlyMembership extends Membership {
    public MonthlyMembership() { this.basePrice = 50.0; }
    @Override public String getTypeName() { return "MONTHLY"; }
    @Override public int getDurationDays() { return 30; }
}
