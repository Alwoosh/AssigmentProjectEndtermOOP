package edu.aitu.oop3.models;

public abstract class Membership {
    protected double basePrice;
    public abstract String getTypeName();
    public abstract int getDurationDays();

    public double getBasePrice() {
        return basePrice;
    }
}
