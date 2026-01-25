package edu.aitu.oop3.services;

import edu.aitu.oop3.models.FitnessClass;
import edu.aitu.oop3.repositories.ClassRepository;
import java.time.LocalDateTime;

public class AdminService {
    private final ClassRepository classRepo;

    public AdminService(ClassRepository classRepo) {
        this.classRepo = classRepo;
    }

    public void addClass(String title, String instructorName, int maxCapacity, LocalDateTime scheduleTime) {
        FitnessClass newClass = new FitnessClass(0, title, instructorName, maxCapacity, scheduleTime);
        classRepo.save(newClass);
        System.out.println("Занятие '" + title + "' успешно добавлено!");
    }

    public void removeClass(int id) {
        FitnessClass fitnessClass = classRepo.findById(id);
        if (fitnessClass != null) {
            classRepo.delete(id);
            System.out.println("Занятие '" + fitnessClass.getTitle() + "' удалено.");
        } else {
            System.out.println("Ошибка: Занятие с ID " + id + " не найдено.");
        }
    }
}
