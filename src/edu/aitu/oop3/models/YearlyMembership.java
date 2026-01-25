package edu.aitu.oop3.models;

public class YearlyMembership extends Membership {
    public YearlyMembership() { this.basePrice = 400.0; }
    @Override public String getTypeName() { return "YEARLY"; }
    @Override public int getDurationDays() { return 365; }
}
