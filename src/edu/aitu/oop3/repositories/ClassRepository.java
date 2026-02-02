package edu.aitu.oop3.repositories;

import java.util.List;
import edu.aitu.oop3.models.FitnessClass;

public interface ClassRepository extends Repository<FitnessClass> {
    int getCurrentBookingCount(int classId);
}
