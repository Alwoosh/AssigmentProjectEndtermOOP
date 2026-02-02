package edu.aitu.oop3.services;

import edu.aitu.oop3.models.FitnessClass;
import edu.aitu.oop3.models.Member;
import edu.aitu.oop3.repositories.ClassRepository;
import edu.aitu.oop3.repositories.MemberRepository;
import edu.aitu.oop3.util.GenericFilter;
import java.time.LocalDateTime;
import java.util.List;

public class AdminService {
    private final ClassRepository classRepo;
    private final MemberRepository memberRepo;

    public AdminService(ClassRepository classRepo, MemberRepository memberRepo) {
        this.classRepo = classRepo;
        this.memberRepo = memberRepo;
    }

    public void extendMemberships(int daysToExpiry, int extensionDays) {
        List<Member> allMembers = memberRepo.findAll();
        GenericFilter<Member> filter = new GenericFilter<>();
        
        LocalDateTime threshold = LocalDateTime.now().plusDays(daysToExpiry);
        
        List<Member> expiringSoon = filter.filter(allMembers, 
            m -> m.getExpiryDate().isBefore(threshold.toLocalDate())
        );

        expiringSoon.forEach(m -> {
            m.setExpiryDate(m.getExpiryDate().plusDays(extensionDays));
            memberRepo.update(m);
            System.out.println("Extended membership for: " + m.getName());
        });
    }

    public void addClass(String title, String instructorName, int maxCapacity, LocalDateTime scheduleTime) {
        FitnessClass newClass = new FitnessClass(0, title, instructorName, maxCapacity, scheduleTime);
        classRepo.save(newClass);
        System.out.println("Class '" + title + "' successfully added!");
    }

    public void removeClass(int id) {
        FitnessClass fitnessClass = classRepo.findById(id);
        if (fitnessClass != null) {
            classRepo.delete(id);
            System.out.println("Class '" + fitnessClass.getTitle() + "' deleted.");
        } else {
            System.out.println("Error: Class with ID " + id + " not found.");
        }
    }
}
