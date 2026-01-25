package edu.aitu.oop3.patterns;

import edu.aitu.oop3.models.Membership;
import edu.aitu.oop3.models.MonthlyMembership;
import edu.aitu.oop3.models.YearlyMembership;

public class MembershipFactory {
    public static Membership createMembership(String type) {
        switch (type.toUpperCase()) {
            case "MONTHLY": return new MonthlyMembership();
            case "YEARLY": return new YearlyMembership();
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
