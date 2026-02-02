package edu.aitu.oop3.models;

public class VisitBasedMembership extends Membership {
    private int visitsLeft;

    public VisitBasedMembership() {
        this.basePrice = 30.0;
        this.visitsLeft = 10;
    }

    @Override public String getTypeName() { return "VISIT_BASED"; }
    @Override public int getDurationDays() { return 90; } // Valid for 90 days or until visits are used

    public int getVisitsLeft() { return visitsLeft; }
    public void useVisit() { if (visitsLeft > 0) visitsLeft--; }
}
