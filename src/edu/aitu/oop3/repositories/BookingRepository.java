package edu.aitu.oop3.repositories;

import edu.aitu.oop3.models.ClassBooking;
import java.util.List;

public interface BookingRepository {
    boolean exists(int memberId, int classId);
    void save(int memberId, int classId);
    List<ClassBooking> findByMember(int memberId);
}
