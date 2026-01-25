package edu.aitu.oop3.repositories;

import java.util.List;
import edu.aitu.oop3.models.FitnessClass;

public interface ClassRepository {
    FitnessClass findById(int id);
    int getCurrentBookingCount(int classId);
    void save(FitnessClass fitnessClass);
    void delete(int id);
    List<FitnessClass> findAll();
}
