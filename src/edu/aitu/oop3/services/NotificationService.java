package edu.aitu.oop3.services;

import edu.aitu.oop3.models.FitnessClass;
import edu.aitu.oop3.models.Member;

public interface NotificationService {
    void sendBookingConfirmation(Member member, FitnessClass fitnessClass);
}
